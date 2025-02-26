package org.example.targeterr;

import org.example.Service;

@Service
public class Annotated {
    private AnnotatedInner annotatedInner;
    private NotAnnotated notAnnotated;

    public Annotated(AnnotatedInner annotatedInner, NotAnnotated notAnnotated) {
        this.annotatedInner = annotatedInner;
        this.notAnnotated = notAnnotated;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
