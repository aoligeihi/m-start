package com.yy.star.demo.java8;

import java.util.Optional;

public class OptionalUsage {

    public static void main(String[] args) {
        Optional<Insurance> insuranceOptional = Optional.<Insurance>empty();

        insuranceOptional.get();

        Optional<Insurance> insuranceOptional1 = Optional.of(new Insurance());

        insuranceOptional1.get();

        Optional<Insurance> objectOptional = Optional.ofNullable(null);

        objectOptional.orElseGet(Insurance::new);

        objectOptional.orElse(new Insurance());

        objectOptional.orElseThrow(RuntimeException::new);

        objectOptional.orElseThrow(() -> new RuntimeException("Not have reference"));


    }
}
