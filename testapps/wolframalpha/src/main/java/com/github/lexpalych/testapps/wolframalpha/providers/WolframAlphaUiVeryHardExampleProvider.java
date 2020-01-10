//package com.github.lexpalych.testapps.wolframalpha.providers;
//
//import org.junit.jupiter.api.extension.ExtensionContext;
//import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
//import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
//
//import java.util.stream.Stream;
//
//import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_UI;
//import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaExample.getExampleList;
//import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaExample.getTestTemplateInvocationContextBuilder;
//
//public class WolframAlphaUiVeryHardExampleProvider implements TestTemplateInvocationContextProvider {
//    @Override
//    public boolean supportsTestTemplate(ExtensionContext context) {
//        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
//        String testMethodName = context.getTestMethod().orElseThrow().getName();
//
//        return testClassName.equals("WolframAlphaUi") && testMethodName.equals("checkCalculator");
//    }
//
//    @Override
//    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
//        return getExampleList("veryComplex")
//                .stream()
//                .map(example -> getTestTemplateInvocationContextBuilder(example, "Очень сложные примеры"))
//                .map(PREPARE_UI);
//
//    }
//}