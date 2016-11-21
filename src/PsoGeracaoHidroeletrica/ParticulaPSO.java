package PsoGeracaoHidroeletrica;

import java.util.Random;

import simulacao.SimulacaoOperacaoEnergeticaPSO;

public class ParticulaPSO {

	private double[][] velocidade;
	private double[][] posicao;
	private double[] posicaomin;
	private double[] posicaomax;
	private double[] velocidademax,velocidademin;
	private double pbest;
	private double[][] vetorpbest;
	private double[] valorAgua;
	
	public ParticulaPSO(int iteracao,int dimensao,int i,double[] xmin,double[] xmax,double[] paramVolumesfinais){
		posicao=new double[iteracao][dimensao];
		velocidade=new double[iteracao][dimensao];
		vetorpbest=new double[iteracao][dimensao];
		posicaomin=new double[dimensao];
		posicaomax=new double[dimensao];
		valorAgua=new double[dimensao];
		velocidademax=new double[dimensao];
		velocidademin=new double[dimensao];
		for(int j=0;j<dimensao;j++){
			if(j<60){
			this.posicaomin[j]=xmin[0];
			this.posicaomax[j]=xmax[0];
			}else{ if(j<120){
				this.posicaomin[j]=xmin[1];
				this.posicaomax[j]=xmax[1];
			}	else{
				this.posicaomin[j]=xmin[2];
				this.posicaomax[j]=xmax[2];
			}
			
			}
		}		
		for(int j=0;j<dimensao;j++){
			if(j<60){
			velocidademin[j]=(xmin[0] - xmax[0])/iteracao;
			velocidademax[j]=(xmax[0]-xmin[0])/iteracao;
			}else{
				if(j<120){
					velocidademin[j]=(xmin[1] - xmax[1])/iteracao;
					velocidademax[j]=(xmax[1]-xmin[1])/iteracao;
				}else{
					velocidademin[j]=(xmin[2] - xmax[2])/iteracao;
					velocidademax[j]=(xmax[2]-xmin[2])/iteracao;
				}
			}
			}
		
		Random geradorposicao = new Random();
		Random geradorposicaopos=new Random();
		
		for(int j=0;j<dimensao;j++){
			//gerar a parte decimal
			double decimal=geradorposicao.nextDouble();
			//gerar
			double neg=(geradorposicao.nextInt(2));
			neg=(-1)*(neg*0.01)*(paramVolumesfinais[j]);
			double pos=(geradorposicaopos.nextInt(3));
			pos=(pos*0.01)*(paramVolumesfinais[j]);
			posicao[0][j]=paramVolumesfinais[j] + pos+neg;
			if(posicao[0][j]<posicaomin[j]){
				posicao[0][j]=posicaomin[j];
			}
			else{
				if(posicao[0][j]>posicaomax[j]){
					posicao[0][j]=posicaomax[j];
				}
			}
			//posicao[0][j]=geradorposicao.nextInt((int) (this.posicaomax[j]-this.posicaomin[j]-1))+this.posicaomin[j]+decimal;
			decimal=geradorposicao.nextDouble();
			//velocidade[0][j]=geradorposicao.nextInt((int) (velocidademax[j]-velocidademin[j]-1))+velocidademin[j]+decimal;
			velocidade[0][j]=0;
			
		}
		
	}
	
	public void AtualizarVelocidade(SimulacaoOperacaoEnergeticaPSO simulacao,int iteracao,double c1,double c2,double r1,double r2,double[][] xxx){
		for(int i=0;i<posicaomin.length;i++){
		velocidade[iteracao][i]=0.001*valorAgua[i]+  velocidade[iteracao-1][i] + c1*r1*(vetorpbest[iteracao-1][i] - posicao[iteracao-1][i])+
				c2*r2*(xxx[iteracao-1][i]-posicao[iteracao-1][i]);
		//verifica se passou dos limites
			if(velocidade[iteracao][i]>velocidademax[i]){
			velocidade[iteracao][i]=velocidademax[i];
			
			}
			if(velocidade[iteracao][i]<velocidademin[i]){
			velocidade[iteracao][i]=velocidademin[i];
			}
	
		}
	}
	
	
	
	public void AtualizarPosicao(int iteracao){
		
		for(int i=0;i<posicaomin.length;i++){		
		posicao[iteracao][i]=posicao[iteracao-1][i]+ velocidade[iteracao][i];
			if(posicao[iteracao][i]>posicaomax[i]){
				posicao[iteracao][i]=posicaomax[i];
			
			}
			if(posicao[iteracao][i]<posicaomin[i]){
				posicao[iteracao][i]=posicaomin[i];
			}
		}
		
	}
	
	
	public void AvaliarParticula(int iteracao,SimulacaoOperacaoEnergeticaPSO simulacao){
		double fitness = 0;
		simulacao.definirVolumesFinais(posicao[iteracao],3, 60);
		fitness=simulacao.simularOperacaoEnergeticaPSO(60);
		simulacao.determinaValorAgua(this.valorAgua);
		//fitnes=(posicao[iteracao][0])*(posicao[iteracao][0]) -(posicao[iteracao][0])*(posicao[iteracao][1]) +(posicao[iteracao][1])*(posicao[iteracao][1]) -3*(posicao[iteracao][1]);
		if(iteracao==0){
			pbest=fitness;
			for (int i = 0; i < posicaomin.length; i++) {
				vetorpbest[iteracao][i]=posicao[iteracao][i];
			}
		}else{
			if(fitness<pbest){
			pbest=fitness;
			for (int i = 0; i < posicaomin.length; i++) {
				vetorpbest[iteracao][i]=posicao[iteracao][i];
			}
			}else{
				for (int i = 0; i < posicaomin.length; i++) {
					vetorpbest[iteracao][i]=vetorpbest[iteracao-1][i];
				}
			}
		}
		
	}
	
	
	public double[] getValorAgua() {
		return valorAgua;
	}

	public void setValorAgua(double[] valorAgua) {
		this.valorAgua = valorAgua;
	}

	public double[][] getV() {
		return velocidade;
	}


	public void setV(double[][] v) {
		this.velocidade = v;
	}


	public double[][] getX() {
		return posicao;
	}


	public void setX(double[][] x) {
		this.posicao = x;
	}

	public double getPbest() {
		return pbest;
	}

	public void setPbest(double pbest) {
		this.pbest = pbest;
	}

	public double[][] getXx() {
		return vetorpbest;
	}

	public void setXx(double[][] xx) {
		this.vetorpbest = xx;
	}


	


	
	
	

}
