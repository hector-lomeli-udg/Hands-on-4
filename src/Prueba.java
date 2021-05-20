package agentsPrueba.src;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.*;

import java.io.*;

public class Prueba extends Agent {

	String texto="";
	private PruebaGui myGui;

	
	protected void setup() {

		myGui = new PruebaGui(this);
		myGui.showGui();

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Prueba-borrador");
		sd.setName("JADE-prueba-tarea");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		addBehaviour(new MyGenericBehaviour());

	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		myGui.dispose();
		System.out.println("Prueba-agent "+getAID().getName()+" finalizando.");
	}

	 
	public void insertarTexto(final String title) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				texto = title;

				System.out.println(texto+" insertado.");
			}
		} );
	}


	private class MyGenericBehaviour extends Behaviour {

		boolean bandera = false;

		public void action() {
			if(bandera == false){
				try{

				System.out.println("AGENTE EXISTIENDO");

				bandera = true;

				}catch(Exception e){
					System.out.println("Ha ocurrido un error");
				}

			} 
			else
				block();
		}


		public boolean done() {
		if (texto == "(exit)")
			return true;
		else
			return false;
		} 
	}

	

	

}