package simulacao;



import java.io.IOException;
import java.util.ArrayList;

import horizonte.HorizontePlanejamento;
import horizonte.IntervaloDeHorizonte;
import print.Gravar;
import print.GravarPSO;
import rede.NoHidroenergetico;
import usina.Usina;
import usina.UsinaHidroeletrica;
import sistema.SistemaHidroeletrico;
import sistema.SistemaHidrotermicoPSO;

public class SimulacaoOperacaoEnergeticaPSO {
	private SistemaHidrotermicoPSO SistemaHidroeletrico;
	private HorizontePlanejamento HorizontePlanejamento;
	private NoHidroenergetico[][]   nos;
	private double[] demanda;
	private double[] valorAgua;
	
	public SimulacaoOperacaoEnergeticaPSO(int intervalos,double demanda){
		SistemaHidroeletrico=new SistemaHidrotermicoPSO();
		HorizontePlanejamento= new HorizontePlanejamento();
		this.demanda=new double[intervalos];
		this.valorAgua=new double[180];
		for(int i=0;i<intervalos;i++){
			this.demanda[i]=demanda;
		}
		
	}
	
	public void definirSistemaHidroeletrico( UsinaHidroeletrica usinas[]){
	
		for(int i=0;i< usinas.length;i++){
			SistemaHidroeletrico.getUsinas().add(usinas[i]);
			
		}
		
		
	}
	
	
	
	
	public void definirHorizontePlanejamento(double paramTaxaJurosNominal,double paramPlanejamentoCustoGeracaoHidrotermica,
			int paramMesInicial,int paramDiscretizacao,int paramTipo,IntervaloDeHorizonte intervalo[]){
		HorizontePlanejamento.setDiscretizacao(paramDiscretizacao);
		HorizontePlanejamento.setMesInical(paramMesInicial);
		HorizontePlanejamento.setTaxaJurosNominal(paramTaxaJurosNominal);
		HorizontePlanejamento.setTipo(paramTipo);
		HorizontePlanejamento.setPlanejamentoCustoGeracaoHidrotermica(paramPlanejamentoCustoGeracaoHidrotermica);
		
		for(int i=0;i<intervalo.length;i++){
			HorizontePlanejamento.getIntervalos().add(intervalo[i]);
		}
		
	}
	
	public void definirVolumesFinais(double[]	volumes,int numUsinas,int numIntervalos){
		//HorizontePlanejamento.setNumeroIntervalos(5);
		int aux;
		for(int i=0;i<numUsinas;i++){
			aux=i*60;
			for(int j=0;j<numIntervalos;j++){
				nos[j][i].setVolumeFinal(volumes[aux]);
				aux++;
			}
		}
		
		
	}
	
	public void definirVazoesAfluentesNaturais(double Afluencias[][],int numUsinas,int numIntervalos){
		//HorizontePlanejamento.setNumeroIntervalos(5);
		nos=new NoHidroenergetico[numIntervalos][numUsinas];
		for(int i=0;i<numIntervalos;i++){
		
			for(int j=0;j<numUsinas;j++){
				NoHidroenergetico no=new NoHidroenergetico();
				nos[i][j]=no;
				nos[i][j].setVazaoAfluenteNatural(Afluencias[i][j]);
			}
		}
		
		
	}
	

	public void definirRestricoesOperativas(double[] restricaoVolume,int j
			,double[] limitesDefluencia,double limiteMaxGH){
		double tamMatriz=nos.length;
		for(int i=0;i<tamMatriz;i++){
			
			nos[i][j].setLimiteMinimoVolume(restricaoVolume[0]);
			nos[i][j].setLimiteMaximoVoume(restricaoVolume[1]);
			nos[i][j].setLimiteMinimoVazaoDefluente(limitesDefluencia[0]);
			nos[i][j].setLimiteMaximoVazaoDefluente(limitesDefluencia[1]);
			nos[i][j].setLimiteMaximoGeracaoHidraulica(limiteMaxGH);
		}
		
		
	}
	
	public void definirMercadoEnergia(){
		
		
	}
	
	public void definirVolumeInicial(double volumesIniciais[]){
		
			for(int i=0;i<SistemaHidroeletrico.getUsinas().size();i++){
				nos[0][i].setVolumeInicial(volumesIniciais[i]);
			}
		
		
	}
	
	public void definirPoliticaOperativaEnergetica(){
		
		
	}
	
	
	
	//metodo de simula��o da gera��o
//	public double simularOperacaoEnergetica(double[] demanda,int numIntervalos){
//		double EASM=SistemaHidroeletrico.EnergiaArmazenadaMaximaNoSistema(nos[0]);
//		double geracao=0;
//		for(int i=0;i<numIntervalos;i++){
//			if(i==0){
//				
//				geracao=SistemaHidroeletrico.Executar(i,0.1,  0.005, demanda[i], nos[i],nos[i], EASM,HorizontePlanejamento.getIntervalos().get(i));
//				HorizontePlanejamento.getIntervalos().get(i).setGeracaoHidraulica(geracao);
//				System.out.println("intervalo " +i+" geracao "+geracao);
//			}
//			else{
//				geracao=SistemaHidroeletrico.Executar(i,0.1,  0.005, demanda[i], nos[i],nos[i-1], EASM,HorizontePlanejamento.getIntervalos().get(i));
//				HorizontePlanejamento.getIntervalos().get(i).setGeracaoHidraulica(geracao);
//
//				System.out.println("intervalo " +i+" geracao "+geracao);
//			}
//			}
//		return geracao;
//		
//	}
	
	
	//metodo de simula��o da gera��o com o PSO
		public double simularOperacaoEnergeticaPSO(int numIntervalos){
			//double EASM=SistemaHidroeletrico.EnergiaArmazenadaMaximaNoSistema(nos[0]);
			double geracao=0;
			double custo = 0;
			
			for(int i=0;i<numIntervalos;i++){
				if(i==0){
					
					geracao=SistemaHidroeletrico.Executar(i, demanda[i], nos[i],nos[i],HorizontePlanejamento.getIntervalos().get(i));
					HorizontePlanejamento.getIntervalos().get(i).setGeracaoHidraulica(geracao);
					if(demanda[i]<geracao){
						custo=custo + Math.pow(demanda[i]-geracao,2);
					}
					if(geracao==0){
						custo=custo+demanda[i]/2;
					}
					System.out.println("intervalo " +i+" geracao "+geracao);
				}
				else{
					geracao=SistemaHidroeletrico.Executar(i, demanda[i], nos[i],nos[i-1],HorizontePlanejamento.getIntervalos().get(i));
					HorizontePlanejamento.getIntervalos().get(i).setGeracaoHidraulica(geracao);
					if(demanda[i]<geracao){
						custo=custo + Math.pow(demanda[i]-geracao,2);
					}
					if(geracao==0){
						custo=custo+demanda[i]/2;
					}
					System.out.println("intervalo " +i+" geracao "+geracao);
				}
				}
			return custo/2;
			
		}
	public double EnergiaArmazenada(){
		double energia=SistemaHidroeletrico.EnergiaArmazenadaNoSistema(nos[59]);
		GravarPSO print=new GravarPSO();
		try {
			print.gravarValores(this, 60, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Nao foi possivel gravar o melhor");
		}
		return energia;
	}
	
	public void determinaValorAgua(double[] valorAgua){
		double customarginalop;
		for(int i=0; i<SistemaHidroeletrico.getUsinas().size();i++){
			for(int j=0;j<60;j++){
				customarginalop=(-1)*(demanda[j]-this.getHorizontePlanejamento().getIntervalos().get(j).getGeracaoHidraulica()
						);
				valorAgua[i*60+j]=(-1)*(demanda[j]-this.getHorizontePlanejamento().getIntervalos().get(j).getGeracaoHidraulica()
						)*(nos[j][i].getAlturaQuedaLiquida()*this.getSistemaHidroeletrico().getUsinas().get(i).getK());
			}
		}
	}

	public SistemaHidrotermicoPSO getSistemaHidroeletrico() {
		return SistemaHidroeletrico;
	}

	public void setSistemaHidroeletrico(SistemaHidrotermicoPSO sistemaHidroeletricopso) {
		SistemaHidroeletrico = sistemaHidroeletricopso;
	}

	public HorizontePlanejamento getHorizontePlanejamento() {
		return HorizontePlanejamento;
	}

	public void setHorizontePlanejamento(HorizontePlanejamento horizontePlanejamento) {
		HorizontePlanejamento = horizontePlanejamento;
	}

	public NoHidroenergetico[][] getNos() {
		return nos;
	}

	public void setNos(NoHidroenergetico[][] nos) {
		this.nos = nos;
	}
	
	
	

}

