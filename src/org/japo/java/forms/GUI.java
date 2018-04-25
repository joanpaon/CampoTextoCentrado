/*
 * Copyright 2017 José A. Pacheco Ondoño - joanpaon@gmail.com.
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
package org.japo.java.forms;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.japo.java.entities.LabeledColor;
import org.japo.java.events.AEM;
import org.japo.java.libraries.UtilesSwing;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_LOOK_AND_FEEL = "look_and_feel";
    public static final String PRP_FAVICON = "favicon";
    public static final String PRP_NUMERO_COLORES = "numero_colores";
    public static final String PRP_FUENTE = "fuente";


    // Valores por Defecto
    public static final String DEF_LOOK_AND_FEEL = UtilesSwing.LNF_NIMBUS;
    public static final String DEF_FAVICON = "img/favicon.png";
    public static final String DEF_NUMERO_COLORES = "0";
    public static final String DEF_FUENTE = "fonts/IndieFlower.ttf";
    
    // Referencias
    private Properties prp;
    private JPanel pnlPpal;
    private JTextField txfColor;

    // Colores
    private final ArrayList<LabeledColor> colores = new ArrayList<>();

    // Constructor
    public GUI(Properties prp) {
        // Inicialización Anterior
        initBefore(prp);

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción del IGU
    private void initComponents() {
        // Campo de Texto
        txfColor = new JTextField();
        txfColor.setFont(UtilesSwing.cargarFuente(
                prp.getProperty(PRP_FUENTE, DEF_FUENTE)).
                deriveFont(Font.BOLD, 30f));
        txfColor.setColumns(10);
        txfColor.setHorizontalAlignment(JTextField.CENTER);
        txfColor.addActionListener(new AEM(this));

        // Panel Principal
        pnlPpal = new JPanel(new GridBagLayout());
        pnlPpal.add(txfColor);

        // Ventana principal
        setContentPane(pnlPpal);
        setTitle("Swing Manual #05");
        setResizable(false);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore(Properties prp) {
        // Memorizar Referencia
        this.prp = prp;

        // Establecer LnF
        UtilesSwing.establecerLnF(prp.getProperty(PRP_LOOK_AND_FEEL, DEF_LOOK_AND_FEEL));

        // Cargar colores
        cargarColores();
    }

    // Inicialización Anterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(PRP_FAVICON, DEF_FAVICON));
    }

    public void procesarTexto(ActionEvent e) {
        // Generador del evento
        JTextField txfActual = (JTextField) e.getSource();

        // Texto tecleado
        String color = txfActual.getText().trim().toUpperCase();

        // Obtener Color
        LabeledColor lc = buscarColor(colores, color);
        
        // Cambiar Color Panel
        pnlPpal.setBackground(lc);
    }

    private void cargarColores() {
        // Posiciones de Colores
        final int POS_NAME = 0;
        final int POS_RED = 1;
        final int POS_GREEN = 2;
        final int POS_BLUE = 3;
        final int POS_ALPHA = 4;
        
        // Proceso de carga
        try {
            // Número de Colores Disponibles
            int numColores = Integer.parseInt(prp.getProperty(PRP_NUMERO_COLORES, DEF_NUMERO_COLORES));

            // Carga los colores
            for (int i = 0; i < numColores; i++) {
                // Carga Propiedad Actual
                String prpAct = prp.getProperty(String.format("color%02d", i + 1));

                // Segrega Campos 
                String[] cmpAct = prpAct.split("\\s*,\\s*");

                // Obtiene Componentes
                String nAct = cmpAct[POS_NAME].toUpperCase();
                int rAct = Integer.parseInt(cmpAct[POS_RED]);
                int gAct = Integer.parseInt(cmpAct[POS_GREEN]);
                int bAct = Integer.parseInt(cmpAct[POS_BLUE]);
                int aAct = Integer.parseInt(cmpAct[POS_ALPHA]);

                // Instancia y añade color
                colores.add(new LabeledColor(nAct, rAct, gAct, bAct, aAct));
            }
        } catch (NumberFormatException e) {
            // Mensaje de error
            System.out.println("ERROR: No se han cargado los colores");
        } finally {
            // Color Extra
            colores.add(new LabeledColor("COLOR_EXTRA", 152, 251, 152, 255));
        }
    }
    
    public static final LabeledColor buscarColor(
            ArrayList<LabeledColor> listaColores, String nombreColor) {
        // Referencia Búsqueda
        LabeledColor lc = null;
        
        // Proceso de búsqueda
        for (LabeledColor colorActual : listaColores) {
            if (colorActual.getName().equals(nombreColor)) {
                lc = colorActual;
            }
        }

        // Devuelve LabeledColor Encontrado
        return lc;
    }
}
