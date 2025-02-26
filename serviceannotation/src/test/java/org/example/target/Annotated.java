package org.example.target;

import org.example.Service;

@Service
public class Annotated {
    private AnnotatedInner annotatedInner;

    public Annotated(AnnotatedInner annotatedInner) {
        this.annotatedInner = annotatedInner;
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
