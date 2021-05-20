package agentsPrueba.src;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

class PruebaGui extends JFrame {

	private Prueba myAgent;
	private JTextField titleField;

	PruebaGui(Prueba a) {
		super(a.getLocalName());
		
		myAgent = a;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2));
		p.add(new JLabel("INSERTAR TEXTO: "));
		titleField = new JTextField(15);
		p.add(titleField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("AGREGAR");
		addButton.addActionListener( new ActionListener() {

			String texto="";
			//String ruta = "C:/Jade 4.5.0/jade/src/agentsPrueba/src/pruebaBase.clp";
			String ruta = "C:/CLIPS 6.31/clips/clips_jni_051/test/agentPrueba/pruebaBase.clp";

			File archivo;
			FileWriter escribir;
			PrintWriter linea;


			public void actionPerformed(ActionEvent ev) {
				try {
					String title = titleField.getText().trim();

					texto = title;

					archivo = new File(ruta);

					try{
					escribir = new FileWriter(archivo.getAbsoluteFile(), true);
					linea = new PrintWriter(escribir);
					linea.write("\n");
					linea.write(texto);
					linea.close();
					escribir.close();

					}catch(IOException ex){
						System.out.println("ERROR CON ARCHIVO");
					}
					
					myAgent.insertarTexto(title);
					titleField.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(PruebaGui.this, "VALORES INVALIDOS. "+e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}


	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}
}
