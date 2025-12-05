package entidades;

import enums.TipoCarga;

public class Carga {
    private TipoCarga tipoCarga;
    private double quantidadeKg;

    public Carga(TipoCarga tipoCarga, double quantidadeKg) {
        this.tipoCarga = tipoCarga;
        this.quantidadeKg = quantidadeKg;
    }

    public TipoCarga getTipoCarga() {
        return this.tipoCarga;
    }

    public double getQuantidadeKg() {
        return this.quantidadeKg;
    }
}
