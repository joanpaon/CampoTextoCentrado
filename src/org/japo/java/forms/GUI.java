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
public final class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_LOOK_AND_FEEL_PROFILE = "form_look_and_feel_profile";
    public static final String PRP_FAVICON_RESOURCE = "form_favicon_resource";
    public static final String PRP_FORM_TITLE = "form_title";
    public static final String PRP_FORM_HEIGHT = "form_height";
    public static final String PRP_FORM_WIDTH = "form_width";
    public static final String PRP_FORM_BACKGROUND_RESOURCE = "form_background_resource";
    public static final String PRP_FORM_FONT_RESOURCE = "form_font_resource";

    // Valores por Defecto
    public static final String DEF_LOOK_AND_FEEL_PROFILE = UtilesSwing.LNF_WINDOWS_PROFILE;
    public static final String DEF_FAVICON_RESOURCE = "img/favicon.png";
    public static final String DEF_FORM_TITLE = "Swing Manual App";
    public static final int DEF_FORM_HEIGHT = 300;
    public static final int DEF_FORM_WIDTH = 500;
    public static final String DEF_FORM_BACKGROUND_RESOURCE = "img/background.jpg";
    public static final String DEF_FORM_FONT_RESOURCE = "fonts/default_font.ttf";

    // Referencias
    private Properties prp;
    private JPanel pnlPpal;
    private JTextField txfColor;

    // Colores
    private final ArrayList<LabeledColor> colores = new ArrayList<>();

    // Constructor
    public GUI(Properties prp) {
        // Conectar Referencia
        this.prp = prp;

        // Inicialización Anterior
        initBefore();

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción del IGU
    private void initComponents() {
        // Otros componentes
        txfColor = new JTextField();
        txfColor.setFont(UtilesSwing.importarFuenteRecurso(
                prp.getProperty(PRP_FORM_FONT_RESOURCE, DEF_FORM_FONT_RESOURCE)).
                deriveFont(Font.BOLD, 40f));
        txfColor.setColumns(10);
        txfColor.setHorizontalAlignment(JTextField.CENTER);
        txfColor.addActionListener(new AEM(this));

        // Panel Principal
        pnlPpal = new JPanel(new GridBagLayout());
        pnlPpal.add(txfColor);

        // Ventana principal
        setContentPane(pnlPpal);
        setTitle(prp.getProperty(PRP_FORM_TITLE, DEF_FORM_TITLE));
        try {
            int height = Integer.parseInt(prp.getProperty(PRP_FORM_HEIGHT));
            int width = Integer.parseInt(prp.getProperty(PRP_FORM_WIDTH));
            setSize(width, height);
        } catch (NumberFormatException e) {
            setSize(DEF_FORM_WIDTH, DEF_FORM_HEIGHT);
        }
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty(
                PRP_LOOK_AND_FEEL_PROFILE, DEF_LOOK_AND_FEEL_PROFILE));

        // Cargar colores
        LabeledColor.cargarColores(prp, colores);
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(
                PRP_FAVICON_RESOURCE, DEF_FAVICON_RESOURCE));
    }

    // Pinta el Panel Principal con el color escrito
    public void procesarTexto(ActionEvent e) {
        // Generador del evento
        JTextField txfActual = (JTextField) e.getSource();

        // Texto tecleado
        String color = txfActual.getText().trim().toUpperCase();

        // Obtener Color
        LabeledColor lc = LabeledColor.buscarColor(colores, color);

        // Cambiar Color Panel
        pnlPpal.setBackground(lc);
    }
}
