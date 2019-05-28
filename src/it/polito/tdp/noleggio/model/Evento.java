package it.polito.tdp.noleggio.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento> {
	
	public enum TipoEvento {
		CLIENTE_ARRIVA,
		AUTO_RESTITUITA
	}
	
	public Evento(LocalTime tempo, TipoEvento tipo) {
		super();
		this.tempo = tempo;
		this.tipo = tipo;
	}

	private LocalTime tempo ;
	private TipoEvento tipo ;
	
	@Override
	public int compareTo(Evento other) {
		
		return this.tempo.compareTo(other.tempo);
	}

	public LocalTime getTempo() {
		return tempo;
	}

	public void setTempo(LocalTime tempo) {
		this.tempo = tempo;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public void setTipo(TipoEvento tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return String.format("tempo= %s,tipo=%s" , tempo ,tipo );
	}



}
