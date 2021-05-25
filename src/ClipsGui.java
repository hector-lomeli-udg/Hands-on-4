package clips.agents;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ClipsGui extends JFrame{

	private ClipsAgent myAgent;
	private JTextField hechosField, reglasField;
	boolean btnAceptar = false;

	public ClipsGui(ClipsAgent a){
		myAgent = a;
		this.setSize(500,250);
		setTitle("Mi agente");
		setLocationRelativeTo(null);
		

		JPanel panel = new JPanel();
		this.getContentPane().add(panel);

		JLabel hechosLabel = new JLabel("HECHOS: ");
		panel.add(hechosLabel);
		hechosField = new JTextField(40);
		panel.add(hechosField);

		JLabel reglasLabel = new JLabel("REGLAS: ");
		panel.add(reglasLabel);
		reglasField = new JTextField(40);
		panel.add(reglasField);

		JButton agregarHechoButton = new JButton("AGREGAR HECHO");
		panel.add(agregarHechoButton);
		agregarHechoButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String stringHecho = hechosField.getText().trim();
					myAgent.imprimeAgregarHecho(stringHecho);
					hechosField.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(ClipsGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );


		JButton agregarReglaButton = new JButton("AGREGAR REGLA");
		panel.add(agregarReglaButton);
		agregarReglaButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String stringRegla = reglasField.getText().trim();
					myAgent.imprimeAgregarRegla(stringRegla);
					reglasField.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(ClipsGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );


		JButton correrButton = new JButton("CORRER ASK Y TELL");
		panel.add(correrButton);
		correrButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					myAgent.correAskTell();
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(ClipsGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );





		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );

		setResizable(false);
	}




	public void showGui() {
		super.setVisible(true);
	}
}