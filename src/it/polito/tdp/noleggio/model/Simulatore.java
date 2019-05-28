package it.polito.tdp.noleggio.model;

import java.time.Duration;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	//coda prioritaria su cui si regge la simulazione
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;
	
	//stato del mondo
	
	private int autoTot;	//parametro fisso
	private int autoDisp;	//tra 0 e autoTot
	
	//parametri simulazione
	
	private LocalTime oraInizio= LocalTime.of(8,0); //non c'è new su questi dati
	private LocalTime oraFine= LocalTime.of(20, 0);
	private Duration intervalloArrivoCliente=Duration.ofMinutes(10);
	private List<Duration> durateNoleggio;
	
	//statistiche raccolta
	private int numeroClientiTot;
	private int numeroClientiInsoddisfatti;
	
	//variabili interne che genera numeri casuali
	private Random rand= new Random();
	
	public Simulatore() {
		
		durateNoleggio= new ArrayList<Duration>();
		durateNoleggio.add(Duration.ofHours(1)) ;
		durateNoleggio.add(Duration.ofHours(2)) ;
		durateNoleggio.add(Duration.ofHours(3)) ;		
	}
	
	public void init(int autoTot) {
		this.autoTot = autoTot;
		this.autoDisp = this.autoTot;
		this.numeroClientiTot = 0;
		this.numeroClientiInsoddisfatti = 0;

		this.queue.clear();
		
		// carico gli eventi iniziali
		// arriva 1 cliente ogni intervalloArrivoCliente
		// a partire dalle oreInizio
		
		for (LocalTime ora = oraInizio; ora.isBefore(oraFine);
				ora = ora.plus(intervalloArrivoCliente)) {
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));
			
		}
		
	}
	public void run() {
		
		while(!queue.isEmpty()) {
			Evento ev=queue.poll() ;
			System.out.println(ev);
			
			switch (ev.getTipo()) {

			case CLIENTE_ARRIVA:
				this.numeroClientiTot++;
				if (this.autoDisp == 0) {
					this.numeroClientiInsoddisfatti++;
				} else {
					// noleggio auto al cliente
					this.autoDisp--;

					int i = rand.nextInt(durateNoleggio.size());
					Duration noleggio = durateNoleggio.get(i);
					LocalTime rientro = ev.getTempo().plus(noleggio);

					queue.add(new Evento(rientro, TipoEvento.AUTO_RESTITUITA));

				}

				break;

			case AUTO_RESTITUITA:
				this.autoDisp++ ;
				break;
			}
		}
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public Duration getIntervalloArrivoCliente() {
		return intervalloArrivoCliente;
	}

	public void setIntervalloArrivoCliente(Duration intervalloArrivoCliente) {
		this.intervalloArrivoCliente = intervalloArrivoCliente;
	}

	public List<Duration> getDurateNoleggio() {
		return durateNoleggio;
	}

	public void setDurateNoleggio(List<Duration> durateNoleggio) {
		this.durateNoleggio = durateNoleggio;
	}

	public int getAutoTot() {
		return autoTot;
	}

	public int getAutoDisp() {
		return autoDisp;
	}

	public int getNumeroClientiTot() {
		return numeroClientiTot;
	}

	public int getNumeroClientiInsoddisfatti() {
		return numeroClientiInsoddisfatti;
	}
	
	

}
