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
import java.util.ArrayList;
import java.util.Properties;
import org.japo.java.libraries.UtilesCSV;
import org.japo.java.libraries.UtilesValidacion;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class LabeledColor extends Color {

    // Posiciones de Colores
    public static final int POS_NAME = 0;
    public static final int POS_RED = 1;
    public static final int POS_GREEN = 2;
    public static final int POS_BLUE = 3;
    public static final int POS_ALPHA = 4;

    // Expresiones Regulares Validación
    public static final String REG_COLOR_NAME = "[a-zA-Z_]+";

    // Propiedades Entidad
    public static final String PRP_NUMERO_COLORES = "numero_colores";

    // Valores por Defecto
    public static final String DEF_COLOR_NAME = "COLOR_EXTRA";
    public static final int DEF_COLOR_COMP_RED = 40;
    public static final int DEF_COLOR_COMP_GREEN = 140;
    public static final int DEF_COLOR_COMP_BLUE = 240;
    public static final int DEF_COLOR_COMP_ALPHA = 255;
    public static final String DEF_NUMERO_COLORES = "0";

    // Campos
    private String name;

    // Constructor Predeterminado
    public LabeledColor() {
        super(
                DEF_COLOR_COMP_RED,
                DEF_COLOR_COMP_GREEN,
                DEF_COLOR_COMP_BLUE,
                DEF_COLOR_COMP_ALPHA);
        this.name = DEF_COLOR_NAME;
    }

    // Constructor Parametrizado
    public LabeledColor(String name, int r, int g, int b, int a) {
        // Instanciar Color Estándar
        super(r, g, b, a);

        // Valida nombre
        if (UtilesValidacion.validar(name, REG_COLOR_NAME)) {
            this.name = name.trim().toUpperCase();
        } else {
            this.name = DEF_COLOR_NAME;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (UtilesValidacion.validar(name, REG_COLOR_NAME)) {
            this.name = name.trim().toUpperCase();
        }
    }

    // Propiedades > Lista ( ArrayList ) de Colores
    public static final void cargarColores(
            Properties prp, ArrayList<LabeledColor> colores) {
        try {
            // Número de Colores Disponibles
            int numColores = Integer.parseInt(prp.getProperty(
                    PRP_NUMERO_COLORES, DEF_NUMERO_COLORES));

            // Carga los colores
            for (int i = 0; i < numColores; i++) {
                // Carga Propiedad Actual
                String prpAct = prp.getProperty(String.format("color%02d", i + 1));

                // Segrega Campos 
                String[] cmpAct = UtilesCSV.convertir(prpAct);

                // Obtiene Componentes
                String nAct = cmpAct[LabeledColor.POS_NAME].toUpperCase();
                int rAct = Integer.parseInt(cmpAct[LabeledColor.POS_RED]);
                int gAct = Integer.parseInt(cmpAct[LabeledColor.POS_GREEN]);
                int bAct = Integer.parseInt(cmpAct[LabeledColor.POS_BLUE]);
                int aAct = Integer.parseInt(cmpAct[LabeledColor.POS_ALPHA]);

                // Instancia y añade color
                colores.add(new LabeledColor(nAct, rAct, gAct, bAct, aAct));
            }
        } catch (NumberFormatException e) {
            System.out.println("ERROR: No se han cargado los colores");
        } finally {
            // Color Mínimo
            colores.add(new LabeledColor());
        }
    }

    // Nombre Color >> Color
    public static final LabeledColor buscarColor(
            ArrayList<LabeledColor> listaColores, String nombreColor) {
        LabeledColor lc = null;

        // Proceso de búsqueda
        for (LabeledColor colorActual : listaColores) {
            if (colorActual.getName().equals(nombreColor)) {
                lc = colorActual;
            }
        }

        return lc;
    }
}
