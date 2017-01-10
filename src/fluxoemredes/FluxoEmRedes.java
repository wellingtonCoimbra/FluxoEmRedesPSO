/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fluxoemredes;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Wellington
 */
public class FluxoEmRedes {
    
    
    public Ciclo procurarCiclo(int[][] MVB,Arco superbasico){
        Ciclo ciclo = null;
        
        
        
        return ciclo;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numUsinas=3;
        int numIntervalos=4;
        System.out.println(numUsinas);
        System.out.println(numIntervalos);
        //emborcacao : 371;251;227;172
        //itumbiara : 1255;850;768; 582;
        //são simão : 1723 ; 1166 ;1054 ;799
        double[][] rede = {{16190,16190,16190,16190,371,251,227,172},{16027,16027,16027,16027,1255,850,768, 582},{11540,11540,11540,11540,1723,1166,1054,799}};
        int[][] velocidade = new int[numUsinas][numIntervalos*2];
        //int[][] MVB = new int[numUsinas][numIntervalos];
        double[] volumeMin = new double[numUsinas];
        double[] volumeMax = new double[numUsinas];
        double[] vazaoMin = new double[numUsinas];
        double[] vazaoMax = new double[numUsinas];
        double demanda = 2500;
        FluxoEmRede fluxoEmRedes = new FluxoEmRede(numUsinas, numIntervalos, rede,volumeMin, volumeMax, vazaoMin,vazaoMax,demanda);
        for(int i = 0; i<1;i++){
            fluxoEmRedes.ParticaoEPATEC();
            System.out.println("i : "+(i+1));
//            fluxoEmRedes.detectarCiclos();
//            fluxoEmRedes.DirecaoDeCaminhadaArcosSuperBasicos();
//            fluxoEmRedes.ProjecaoDeCaminhadaArcosSuperBasicos();
//            fluxoEmRedes.DirecaoDeCaminhadaArcosBasicos();
//            fluxoEmRedes.PassoMaximoDoCiclo();
//            fluxoEmRedes.Atualizar();
        }
        System.out.println("");
//        MVB[0][0] = 1;
//        MVB[0][1] = 0;
//        MVB[0][2] = 0;
//        MVB[0][3] = 1;
//        MVB[1][0] = 1;     
//        MVB[1][1] = 0;      
//        MVB[1][2] = 0;
//        MVB[1][3] = 1;
//        MVB[2][0] = 1;       
//        MVB[2][1] = 0;        
//        MVB[2][2] = 0;
//        MVB[2][3] = 1;
//        MVB[3][0] = 1;       
//        MVB[3][1] = 0;        
//        MVB[3][2] = 0;
//        MVB[3][3] = 1;
        
//        ArrayList<Arco> superbasicos = new ArrayList();
//        Arco superbasico1 = new Arco(0,1,1,1);
//        Arco superbasico2 = new Arco(1,1,2,1);
//        Arco superbasico3 = new Arco(2,1,3,1);
//        Arco superbasico4 = new Arco(3,1,4,1);
//        
//        superbasicos.add(superbasico1);
//        superbasicos.add(superbasico2);
//        superbasicos.add(superbasico3);
//        superbasicos.add(superbasico4);
//        ArrayList<Ciclo> ciclo=new ArrayList();
//        ArrayList<Arco> arcosBasicos = new ArrayList();
//        for(int i=0;i<superbasicos.size();i++){
//            Ciclo ciclo1 = new Ciclo(superbasicos.get(i));
//            ciclo.add(ciclo1);
//            if(i==3){
//                System.out.println("");
//            }
////            ciclo.get(i).trilhaDestino(superbasico3, MVB, rede, numIntervalos);
////            ciclo.get(i).trilhaDestino(superbasicos.get(i), MVB,rede,numIntervalos,arcosBasicos);
////            ciclo.get(i).trilhaOrigem(superbasicos.get(i), MVB,rede,numIntervalos,arcosBasicos);
//            ciclo.get(i).trilhaDestinoOrigem(MVB, rede);
//        }
//        for (Ciclo ciclos : ciclo) {
//            System.out.println("Arco Superbasico");
//            System.out.println(" origem usina : " + ciclos.getSuperbasico().getOrigem()[0]);
//            System.out.println(" origem intervalo :" + ciclos.getSuperbasico().getOrigem()[1]);
//            System.out.println(" destino usina : " + ciclos.getSuperbasico().getDestino()[0]);
//            System.out.println(" destino intervalo :" + ciclos.getSuperbasico().getDestino()[1]);
//            
//            System.out.println("\n\nArcos Basico\n\n");
//            for(int i=0;i<ciclos.getArestasbasicas().size();i++){
//                if(!(ciclos.isRepetido(arcosBasicos, ciclos.getArestasbasicas().get(i)))){
//                    arcosBasicos.add(ciclos.getArestasbasicas().get(i));
//                }else{
//                    System.out.println("\n\n\n\nREPETIDO\n\n\n");
//                }
//                System.out.println(" origem usina : " + ciclos.getArestasbasicas().get(i).getOrigem()[0]);
//                System.out.println(" origem intervalo :" + ciclos.getArestasbasicas().get(i).getOrigem()[1]);
//                System.out.println(" destino usina : " + ciclos.getArestasbasicas().get(i).getDestino()[0]);
//                System.out.println(" destino intervalo :" + ciclos.getArestasbasicas().get(i).getDestino()[1]);
//                System.out.println("\n\n");
//            }
//        }
//        System.out.println("");
        

//        //calcular variaveis basicas
//        int aux=0;
//        for(int i=0;i<MVB.length;i++){
//            for(int j=0;j<MVB[0].length;j++){
//                if(MVB[i][j]==0){
//                    Arco arco = new Arco(i,j,i,(j+1));
//                    arcosBasicos.add(arco);
//                    for(int k=0;k<ciclo.size();k++){
//                        for(int l=0;l<ciclo.get(k).getArestasbasicas().size();l++){
//                            Arco arco2 = ciclo.get(k).getArestasbasicas().get(l);
//                            if((arco2.getOrigem()[0]==arco.getOrigem()[0])&&(arco2.getOrigem()[1]==arco.getOrigem()[1])&&(arco2.getDestino()[0]==
//                                    arco.getDestino()[0])&&(arco2.getDestino()[1]==arco.getDestino()[1])){
//                                System.out.println("\n\nENTROU\n\n");
//                            
//                            arcosBasicos.get(aux).getSuperbasico().add(ciclo.get(k).getSuperbasico());
//                            }
//                        }
//                    }
//                }else{
//                    Arco arco = new Arco(i,j,(i+1),j);
//                    arcosBasicos.add(arco);
//                    for(int k=0;k<ciclo.size();k++){
//                        for(int l=0;l<ciclo.get(k).getArestasbasicas().size();l++){
//                            Arco arco2 = ciclo.get(k).getArestasbasicas().get(l);
//                            if((arco2.getOrigem()[0]==arco.getOrigem()[0])&&(arco2.getOrigem()[1]==arco.getOrigem()[1])&&(arco2.getDestino()[0]==
//                                    arco.getDestino()[0])&&(arco2.getDestino()[1]==arco.getDestino()[1])){
//                                System.out.println("\n\nENTROU\n\n");
//                            
//                            arcosBasicos.get(aux).getSuperbasico().add(ciclo.get(k).getSuperbasico());
//                            }
//                        }
//                       
//                    }
//                }
//             aux++;   
//            }
//        }
//            
//           for(int i=0;i<arcosBasicos.size();i++){
//            System.out.println("Aresta basicas");
//                System.out.print(" origem usina : " + arcosBasicos.get(i).getOrigem()[0]);
//                System.out.println(" origem intervalo : "+ arcosBasicos.get(i).getOrigem()[1]);
//                System.out.print("destino usina : " + arcosBasicos.get(i).getDestino()[0]);
//                System.out.println(" destino intervalo : "+ arcosBasicos.get(i).getDestino()[1]);
//                System.out.println(" Arcos superbasicos desse arco basico");
//                for(int j=0;j<arcosBasicos.get(i).getSuperbasico().size();j++){
//                    System.out.println("origem usina arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getOrigem()[0]);
//                    System.out.println("origem intervalo arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getOrigem()[1]);
//                    System.out.println("destino usina arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getDestino()[0]);
//                    System.out.println("destino intervalo arco super basico :" +arcosBasicos.get(i).getSuperbasico().get(j).getDestino()[1]);
//
//                }
//            
//            
//        }
//        for(int i=0;i<numUsinas;i++){
//            for(int j=0;j<numIntervalos;j++){
//                MVB[i][j] = 1;
//            }
//        }
        
        // TODO code application logic here
    }
    
}
