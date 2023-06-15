package com.github.pielena.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes(
        "com.github.pielena.processor.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements
                    = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : annotatedElements) {
                try {
                    writeToStringFile(element, annotation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private void writeToStringFile(Element element, TypeElement annotation) throws IOException {

        String packageName = ((PackageElement) element.getEnclosingElement()).getQualifiedName().toString();
        String fullClassName = ((TypeElement) element).getQualifiedName().toString();
        String classDeclare = "public class " + element.getSimpleName() + annotation.getSimpleName()
                + " extends " + element.getSimpleName() + "{";

        JavaFileObject toStringFile = processingEnv.getFiler()
                .createSourceFile(fullClassName + annotation.getSimpleName(), element);
        try (PrintWriter out = new PrintWriter(toStringFile.openWriter())) {

            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }

            out.println(classDeclare);
            out.println("    @Override");
            out.println("    public String toString() {");
            out.println("        return \"What a surprise from " + element.getSimpleName() + "!\"; ");
            out.println("    }");
            out.println("}");
        }
    }
}
