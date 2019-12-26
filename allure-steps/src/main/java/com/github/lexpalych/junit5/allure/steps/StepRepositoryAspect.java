package com.github.lexpalych.junit5.allure.steps;

import static io.qameta.allure.util.AspectUtils.getName;
import static io.qameta.allure.util.AspectUtils.getParameters;
import static io.qameta.allure.util.ResultsUtils.getStatus;
import static io.qameta.allure.util.ResultsUtils.getStatusDetails;
import static java.util.stream.Collectors.toSet;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.util.AnnotatedTypeScanner;

@Aspect
public final class StepRepositoryAspect {
  private final Set<Class<?>> STEP_REPOSITORY_INTERFACES =
      new AnnotatedTypeScanner(true, StepRepository.class)
          .findTypes("").stream().filter(Class::isInterface).collect(toSet());

  @Pointcut("execution(public * *..*(..))")
  public void anyPublicMethod() {}

  @Pointcut("@this(PageObject)")
  public void withAnnotation() {}

  @Around("anyPublicMethod() && withAnnotation()")
  public Object processPageObjectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    Class<?>[] implementedInterfaces = getImplementedInterfaces(methodSignature);
    Set<Class<?>> stepRepositoryInterfaces =
        getImplementedStepRepositoryInterfaces(implementedInterfaces);

    if (!stepRepositoryInterfaces.isEmpty()) {
      return findAndProcessMethod(joinPoint, methodSignature, stepRepositoryInterfaces);
    }

    return proceed(joinPoint);
  }

  private Object findAndProcessMethod(
                                      ProceedingJoinPoint joinPoint,
                                      MethodSignature methodSignature,
                                      Set<Class<?>> stepRepositoryInterfaces
                                    ) throws Throwable {

    Method foundMethod = searchForMethodInImplementedStepRepositories(stepRepositoryInterfaces, methodSignature);

    if (foundMethod != null) {
      startStep(joinPoint, methodSignature, foundMethod);
      try {
        Object res = proceed(joinPoint);
        stopSuccessfulStep();
        return res;
      } catch (Throwable throwable) {
        stopBrokenStep(throwable);
        throw throwable;
      }
    }

    return proceed(joinPoint);
  }

  private void startStep(JoinPoint joinPoint, MethodSignature methodSignature, Method foundMethod) {
    Step step = foundMethod.getDeclaredAnnotation(Step.class);

    String name = getName(step.value(), joinPoint);
    List<Parameter> parameters = getParameters(methodSignature, joinPoint.getArgs());

    String uuid = UUID.randomUUID().toString();

    StepResult result = new StepResult().setName(name).setParameters(parameters);

    getLifecycle().startStep(uuid, result);
  }

  private void stopSuccessfulStep() {
    getLifecycle().updateStep(s -> s.setStatus(Status.PASSED));
    getLifecycle().stopStep();
  }

  private void stopBrokenStep(Throwable throwable) {
    getLifecycle()
        .updateStep(
            s ->
                s.setStatus(getStatus(throwable).orElse(Status.BROKEN))
                    .setStatusDetails(getStatusDetails(throwable).orElse(null)));
    getLifecycle().stopStep();
  }

  private Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
    return joinPoint.proceed(joinPoint.getArgs());
  }

  private Method searchForMethodInImplementedStepRepositories(Set<Class<?>> stepRepositoryInterfaces, MethodSignature signature) {
    return stepRepositoryInterfaces.stream()
        .flatMap(type -> Stream.of(type.getDeclaredMethods()))
        .filter(Method::isDefault)
        .filter(method -> sameSignature(signature.getMethod(), method))
        .findAny()
        .orElse(null);
  }

  private Set<Class<?>> getImplementedStepRepositoryInterfaces(Class<?>[] implementedInterfaces) {
    return Stream.of(implementedInterfaces)
        .filter(STEP_REPOSITORY_INTERFACES::contains)
        .collect(toSet());
  }

  private Class<?>[] getImplementedInterfaces(MethodSignature methodSignature) {
    return methodSignature.getMethod().getDeclaringClass().getInterfaces();
  }

  private boolean sameSignature(Method first, Method second) {
    return first.getName().equals(second.getName())
        && Arrays.equals(first.getParameterTypes(), second.getParameterTypes());
  }

  /** Only for testing */
  Set<Class<?>> getStepRepositoryInterfaces() {
    return STEP_REPOSITORY_INTERFACES;
  }

  /** Only for testing */
  AllureLifecycle getLifecycle() {
    return Allure.getLifecycle();
  }
}
