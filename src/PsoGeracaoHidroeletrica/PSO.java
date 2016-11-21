package PsoGeracaoHidroeletrica;

import java.io.IOException;
import java.util.ArrayList;

import simulacao.CarregarSistema;
//import simulacao.SimulacaoOperacaoEnergetica;
import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class PSO {
	private double c1=2;
	private double c2=2;
	private double r1;
	private double r2;
	private double[] xmin;
	private double[] xmax;
	private double[] volumesfinais_simulacao;
	private SimulacaoOperacaoEnergeticaPSO simulacaoHidroeletrica;
	private CarregarSistema carregar=new CarregarSistema();
	private ArrayList<ParticulaPSO> particulas;
	private int dimensao;
	private int numeroParticulas;
	private double gbest;
	private double[][] vetorGbest;
	private int iteracoes;
	private int g;
	public PSO(double demanda,int numeroParticulas,int dimensao,int iteracoes,double c1,double c2
			,double[] paramVolumesfinais){
		this.numeroParticulas=numeroParticulas;
		this.dimensao=dimensao;
		this.iteracoes=iteracoes;
		this.volumesfinais_simulacao=paramVolumesfinais;
		this.xmin=new double[3];
		this.xmax=new double[3];
		this.xmin[0]=4250;this.xmin[1]=5447;this.xmin[2]=7238;
		this.xmax[0]=19528;this.xmax[1]=34116;this.xmax[2]=10782;
		particulas=new ArrayList<>();
		vetorGbest=new double[iteracoes][dimensao];
		this.c1=c1;
		this.c2=c2;
		simulacaoHidroeletrica=new SimulacaoOperacaoEnergeticaPSO(60,demanda);
		try {
			carregar.testarSimulacaoUsinasReservatorio(simulacaoHidroeletrica);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erro ao carregar o sistema hidroeletrico");
		}
	}
	
	public void InicializarParticulas(){
		for(int i=0;i<numeroParticulas;i++){
			ParticulaPSO particula=new ParticulaPSO(iteracoes,dimensao,i,xmin,xmax,this.volumesfinais_simulacao); 
			particulas.add(particula);
		}
	}
	
	public void AvaliarParticulas(int iteracao){
		for(int i=0;i<numeroParticulas;i++){
		
			particulas.get(i).AvaliarParticula(iteracao,simulacaoHidroeletrica);
		}
		
	}
	
	
	public void AtualizarVelocidade(int iteracao){
		
		
		for(int i=0;i<numeroParticulas;i++){
			particulas.get(i).AtualizarVelocidade(simulacaoHidroeletrica,iteracao, c1, c2, r1, r2, vetorGbest);
			
		}
		
	}
	
	
	public void AtualizarPosicao(int iteracao){
		
		for(int i=0;i<numeroParticulas;i++){
			particulas.get(i).AtualizarPosicao(iteracao);
			
		}
		
	}
	
	public void ObterGbest(int iteracao){
		double gbestIteracao;
		gbestIteracao=particulas.get(0).getPbest();
		int gIteracao=0;
		// obter o melhor da iteracao
		for(int i=1;i<numeroParticulas;i++){
			if(gbestIteracao>particulas.get(i).getPbest()){
				gIteracao=i;
				gbestIteracao=particulas.get(i).getPbest();
			}
		}
		//se o melhor da iteracao for melhor que o gbest
		if((gbest>gbestIteracao)||(iteracao==0)){
			gbest=gbestIteracao;
			g=gIteracao;
			for(int i=0;i<dimensao;i++){
				vetorGbest[iteracao][i]=particulas.get(g).getX()[iteracao][i];
			}
		}
		//se algum da atual iteracao nao for melhor e pq ate o momento o melhor tbm e o melhor da iteracao anterior
		else{
			for(int i=0;i<dimensao;i++){
				vetorGbest[iteracao][i]=vetorGbest[iteracao-1][i];
			}
		}
	}
	
	
	public void InicializarPbestGbest(){
		double fitnes = 0;

		for(int i=0;i<numeroParticulas;i++){
			double[][] posicao=particulas.get(i).getX();
			double x1=posicao[0][0];
			double x2=posicao[0][1];
			//fitnes=(x1*x1) -(x1*x2)+(x2*x2)-(3*x2);
			particulas.get(i).setPbest(fitnes);
			particulas.get(i).getXx()[0][0]=posicao[0][0];
			particulas.get(i).getXx()[0][1]=posicao[0][1];
			if(i==0){
				gbest=fitnes;
				g=i;
				for(int j=0;j<dimensao;j++){
					vetorGbest[0][i]=posicao[0][i];
				}

			}else{
				if(fitnes<gbest){
					g=i;
					gbest=fitnes;
					vetorGbest[0][0]=posicao[0][0];
					vetorGbest[0][1]=posicao[0][1];
				}
			}
			particulas.get(i).setPbest(fitnes);
		}
	}
	
	
	
	public double[] executar(){
		InicializarParticulas();
		AvaliarParticulas(0);
		ObterGbest(0);
		for(int i=1;i<iteracoes;i++){
			
			AtualizarVelocidade(i);
			AtualizarPosicao(i);
			AvaliarParticulas(i);
			ObterGbest(i);
			
		}
		System.out.println("melhor");
//		particulas.get(g).AvaliarParticula(99, simulacaoHidroeletrica);
		
		return vetorGbest[iteracoes-1];
	}
	
	public void EnergiaArmazenadaMelhor(){
		simulacaoHidroeletrica.definirVolumesFinais(particulas.get(g).getX()[99],3, 60);
		simulacaoHidroeletrica.simularOperacaoEnergeticaPSO(60);
		simulacaoHidroeletrica.determinaValorAgua(particulas.get(g).getValorAgua());
		//simulacaoHidroeletrica.definirVolumesFinais(particulas.get(g).getX()[99], 3,60);
		System.out.println("energia melhor-> "+simulacaoHidroeletrica.EnergiaArmazenada());
	}
	

}
