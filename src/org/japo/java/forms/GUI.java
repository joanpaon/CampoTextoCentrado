/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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

    // Colores
    private final ArrayList<LabeledColor> COLORES = new ArrayList<>();

    // Referencias
    private final Properties prp;

    // Componentes
    private JTextField txfColor;
    private JPanel pnlPpal;

    // Fuentes
    private Font fntColor;

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

    // Construcción - GUI
    private void initComponents() {
        // Campo de Texto de Color
        txfColor = new JTextField();
        txfColor.setHorizontalAlignment(JTextField.CENTER);

        // Panel Principal
        pnlPpal.setLayout(new GridBagLayout());
        pnlPpal.add(txfColor);

        // Ventana Principal
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty("look_and_feel_profile"));

        // Cargar colores
        LabeledColor.cargarColores(prp, COLORES);

        // Fuentes
        fntColor = UtilesSwing.generarFuenteRecurso(prp.getProperty("font_resource"));

        // Panel Principal
        pnlPpal = new JPanel();

        // Ventana Principal
        setContentPane(pnlPpal);
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty("img_favicon_resource"));

        // Campo de Texto de Color
        txfColor.setFont(fntColor.deriveFont(Font.BOLD, 40f));
        txfColor.setColumns(10);
        
        // Ventana Principal - Propiedades
        setTitle(prp.getProperty("form_title"));
        int width = Integer.parseInt(prp.getProperty("form_width"));
        int height = Integer.parseInt(prp.getProperty("form_height"));
        setSize(width, height);
        setLocationRelativeTo(null);

        // Registra los Gestores de Eventos
        txfColor.addActionListener(new AEM(this));
    }

    // Pinta el Panel Principal con el color escrito
    public void procesarAccion(ActionEvent e) {
        // Generador del evento
        JTextField txfActual = (JTextField) e.getSource();

        // Texto tecleado
        String color = txfActual.getText().trim().toUpperCase();

        // Obtener Color
        LabeledColor lc = LabeledColor.buscarColor(COLORES, color);

        // Cambiar Color Panel
        getContentPane().setBackground(lc);
    }
}
