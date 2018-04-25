/*
 * Copyright 2018 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.entities;

import java.awt.Color;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class LabeledColor extends Color implements Comparable<LabeledColor> {

    // Campos
    private String name;

    // Constructor Parametrizado
    public LabeledColor(String name, int r, int g, int b, int a) {
        // Instanciar Color
        super(r, g, b, a);

        if (name != null && name.trim().length() > 0) {
            this.name = name.trim().toUpperCase();
        } else {
            this.name = "SIN_NOMBRE";
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && name.trim().length() > 0) {
            this.name = name.trim().toUpperCase();
        }
    }

    @Override
    public int compareTo(LabeledColor lc) {
        return name.compareTo(lc.getName());
    }
}
