package com.mkyong.order;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

import java.util.Comparator;

public class ParameterCountOrder implements MethodOrderer {

    private Comparator<MethodDescriptor> comparator =
            Comparator.comparingInt(md1 -> md1.getMethod().getParameterCount());

    @Override
    public void orderMethods(MethodOrdererContext context) {

        context.getMethodDescriptors().sort(comparator.reversed());
        //context.getMethodDescriptors().sort(comparator);
    }

}
