package com.github.lexpalych.junit5.allure.steps;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.lexpalych.junit5.allure.steps.steprepositories.ButtonStepRepository;
import com.github.lexpalych.junit5.allure.steps.steprepositories.FieldStepRepository;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.StepResult;
import java.lang.reflect.Method;
import java.util.stream.Stream;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class StepRepositoryAspectTests {
  private static class NoImplementedInterfaces {}

  private static class SweetPageObject
      implements ButtonStepRepository<SweetPageObject>, FieldStepRepository<SweetPageObject> {}

  @ParameterizedTest
  @MethodSource("getMocks")
  void processStepMethodNoImplementedStepRepositoriesTest(
      StepRepositoryAspect stepRepositoryAspect,
      AllureLifecycle lifecycle,
      ProceedingJoinPoint proceedingJoinPoint,
      Method method)
      throws Throwable {
    Object[] args = new Object[] {1, 2, 3};

    when(proceedingJoinPoint.getArgs()).thenReturn(args);
    doReturn(NoImplementedInterfaces.class).when(method).getDeclaringClass();

    stepRepositoryAspect.processPageObjectMethod(proceedingJoinPoint);

    verifyStepsAreNotCreated(lifecycle);
    verifyInterceptedMethodRunsOnlyOneTime(proceedingJoinPoint, args);
  }

  @ParameterizedTest
  @MethodSource("getMocks")
  void processStepMethodNotFoundInStepRepositoriesTest(
      StepRepositoryAspect stepRepositoryAspect,
      AllureLifecycle lifecycle,
      ProceedingJoinPoint proceedingJoinPoint,
      Method method)
      throws Throwable {
    String methodName = "blah";
    Object[] args = new Object[] {1, 2, 3};
    Class<?>[] parameterTypes = new Class<?>[] {Integer.class, Integer.class, Integer.class};

    when(proceedingJoinPoint.getArgs()).thenReturn(args);
    when(method.getName()).thenReturn(methodName);
    when(method.getParameterTypes()).thenReturn(parameterTypes);
    doReturn(SweetPageObject.class).when(method).getDeclaringClass();

    stepRepositoryAspect.processPageObjectMethod(proceedingJoinPoint);

    verifyStepsAreNotCreated(lifecycle);
    verifyInterceptedMethodRunsOnlyOneTime(proceedingJoinPoint, args);
  }

  @ParameterizedTest
  @MethodSource("getMocks")
  void processStepMethodImplementedStepRepositoriesTest(
      StepRepositoryAspect stepRepositoryAspect,
      AllureLifecycle lifecycle,
      ProceedingJoinPoint proceedingJoinPoint,
      Method method,
      MethodSignature methodSignature)
      throws Throwable {
    String methodName = "clickButton";
    String[] parameterNames = new String[] {"name"};
    Class<?>[] parameterTypes = new Class<?>[] {String.class};
    Object[] args = new Object[] {"F to pay respects"};

    when(proceedingJoinPoint.getArgs()).thenReturn(args);
    when(methodSignature.getParameterNames()).thenReturn(parameterNames);
    when(method.getName()).thenReturn(methodName);
    when(method.getParameterTypes()).thenReturn(parameterTypes);
    doReturn(SweetPageObject.class).when(method).getDeclaringClass();

    stepRepositoryAspect.processPageObjectMethod(proceedingJoinPoint);

    verifyStepsAreCreated(lifecycle);
    verifyInterceptedMethodRunsOnlyOneTime(proceedingJoinPoint, args);
  }

  @ParameterizedTest
  @MethodSource("getMocks")
  void processStepMethodThrowsExceptionTest(
      StepRepositoryAspect stepRepositoryAspect,
      AllureLifecycle lifecycle,
      ProceedingJoinPoint proceedingJoinPoint,
      Method method,
      MethodSignature methodSignature)
      throws Throwable {
    class VeryScaryException extends RuntimeException {}

    String methodName = "clickButton";
    String[] parameterNames = new String[] {"name"};
    Class<?>[] parameterTypes = new Class<?>[] {String.class};
    Object[] args = new Object[] {"F to pay respects"};

    when(proceedingJoinPoint.getArgs()).thenReturn(args);
    when(proceedingJoinPoint.proceed(args)).thenThrow(VeryScaryException.class);
    when(methodSignature.getParameterNames()).thenReturn(parameterNames);
    when(method.getName()).thenReturn(methodName);
    when(method.getParameterTypes()).thenReturn(parameterTypes);
    doReturn(SweetPageObject.class).when(method).getDeclaringClass();

    assertThrows(
        VeryScaryException.class,
        () -> stepRepositoryAspect.processPageObjectMethod(proceedingJoinPoint));

    verifyStepsAreCreated(lifecycle);
    verifyInterceptedMethodRunsOnlyOneTime(proceedingJoinPoint, args);
  }

  @Test
  void anyPublicMethodTest() {
    assertDoesNotThrow(() -> new StepRepositoryAspect().anyPublicMethod());
  }

  @Test
  void withAnnotationTest() {
    assertDoesNotThrow(() -> new StepRepositoryAspect().withAnnotation());
  }

  private void verifyStepsAreCreated(AllureLifecycle lifecycle) {
    verify(lifecycle, times(1)).startStep(anyString(), any(StepResult.class));
    verify(lifecycle, times(1)).updateStep(any());
    verify(lifecycle, times(1)).stopStep();

    verify(lifecycle, never()).startStep(anyString(), anyString(), any(StepResult.class));
    verify(lifecycle, never()).updateStep(any(), any());
    verify(lifecycle, never()).stopStep(anyString());
  }

  private void verifyInterceptedMethodRunsOnlyOneTime(
      ProceedingJoinPoint proceedingJoinPoint, Object[] args) throws Throwable {
    verify(proceedingJoinPoint, never()).proceed();
    verify(proceedingJoinPoint, times(1)).proceed(args);
  }

  private void verifyStepsAreNotCreated(AllureLifecycle lifecycle) {
    verify(lifecycle, never()).startStep(anyString(), any(StepResult.class));
    verify(lifecycle, never()).startStep(anyString(), anyString(), any(StepResult.class));

    verify(lifecycle, never()).updateStep(any());
    verify(lifecycle, never()).updateStep(any(), any());

    verify(lifecycle, never()).stopStep();
    verify(lifecycle, never()).stopStep(anyString());
  }

  private static Stream<Arguments> getMocks() {
    StepRepositoryAspect stepRepositoryAspect = spy(StepRepositoryAspect.class);
    AllureLifecycle allureLifecycle = mock(AllureLifecycle.class);
    ProceedingJoinPoint proceedingJoinPoint = mock(ProceedingJoinPoint.class);
    MethodSignature methodSignature = mock(MethodSignature.class);
    Method method = mock(Method.class);

    when(stepRepositoryAspect.getLifecycle()).thenReturn(allureLifecycle);
    when(proceedingJoinPoint.getSignature()).thenReturn(methodSignature);
    when(methodSignature.getMethod()).thenReturn(method);

    assertFalse(stepRepositoryAspect.getStepRepositoryInterfaces().isEmpty());

    return Stream.of(
        Arguments.of(
            stepRepositoryAspect, allureLifecycle, proceedingJoinPoint, method, methodSignature));
  }
}
