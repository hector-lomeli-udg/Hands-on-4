package clips.agents;

import jade.core.Agent;
import jade.core.behaviours.*;

import net.sf.clipsrules.jni.*;

import java.util.*;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class ClipsAgent extends Agent {

  private Hashtable<Integer, String> hashHechos;
  private Hashtable<Integer, String> hashReglas;
  Integer iteracionHecho = 1;
  Integer iteracionRegla = 1;
  private ClipsGui myGui;
  Environment clips;
  String stringAgregarHecho = "";
  String stringAgregarRegla = "";
  boolean bandera = false;
  boolean tellDone = false;
  boolean askDone = false;
  
  protected void setup() {
    hashHechos = new Hashtable<Integer, String>();
    hashReglas = new Hashtable<Integer, String>();
    DFAgentDescription dfd = new DFAgentDescription();
    dfd.setName(getAID());
    ServiceDescription sd = new ServiceDescription();
    sd.setType("Clips-agent");
    sd.setName("JADE-CLIPS-tarea");
    dfd.addServices(sd);
    try {
      DFService.register(this, dfd);
    }
    catch (FIPAException fe) {
      fe.printStackTrace();
    }



    myGui = new ClipsGui(this);
    myGui.showGui();
    clips = new Environment();
    //addBehaviour(new MyGenericBehaviour()); 
    //addBehaviour(new TellBehaviour());
    //addBehaviour(new AskBehaviour());
  } 


  protected void takeDown() {
    try {
      DFService.deregister(this);
    }
    catch (FIPAException fe) {
      fe.printStackTrace();
    }

    myGui.dispose();
    System.out.println("AGENTE GUI FINALIZADO.");
  }



  public void imprimeAgregarHecho(final String textAgregarHecho) {
        addBehaviour(new OneShotBehaviour() {
          public void action() {
            stringAgregarHecho = textAgregarHecho;
            hashHechos.put(iteracionHecho, stringAgregarHecho);
            System.out.println("El hecho: " + stringAgregarHecho + " ha sido insertado.");
            iteracionHecho += 1;
          }
        });
      }


  public void imprimeAgregarRegla(final String textAgregarRegla) {
        addBehaviour(new OneShotBehaviour() {
          public void action() {
            stringAgregarRegla = textAgregarRegla;
            hashReglas.put(iteracionRegla, stringAgregarRegla);
            System.out.println("La regla: " + stringAgregarRegla + " ha sido insertado.");
            iteracionRegla += 1;
          }
        });
      }


  public void correAskTell(){
        addBehaviour(new Behaviour(){
            public void action(){
              Enumeration<String> enumeracionHechos = hashHechos.elements();
              Enumeration<Integer> enumeracionHechosKeys = hashHechos.keys();

              Enumeration<String> enumeracionReglas = hashReglas.elements();
              Enumeration<Integer> enumeracionReglasKeys = hashReglas.keys();

              System.out.println("Tell is executed");
                clips.eval("(clear)");
                //clips.build("(defrule r1 (sintoma ?x) => (printout t ?x crlf))");
                while(enumeracionReglas.hasMoreElements() && enumeracionReglasKeys.hasMoreElements()){
                  //System.out.println("Posicion: "+ enumeracionMemoriaKeys.nextElement() + ". Dato: " + enumeracionMemoria.nextElement());
                  clips.build(enumeracionReglas.nextElement());
                }

                clips.eval("(reset)");

                //clips.eval("(assert (sintoma a))");
                //clips.eval("(assert (sintoma b))");
                while(enumeracionHechos.hasMoreElements() && enumeracionHechosKeys.hasMoreElements()){
                  //System.out.println("Posicion: "+ enumeracionMemoriaKeys.nextElement() + ". Dato: " + enumeracionMemoria.nextElement());
                  clips.eval(enumeracionHechos.nextElement());
                }

                clips.eval("(facts)");
                tellDone = true;


                System.out.println("Ask is executed");
                clips.run();
                askDone = true;
              }

              public boolean done() {
                  if (tellDone == true && askDone == true)
                    return true;
                  else
                    return false;
              } 
        });
      }
       
}