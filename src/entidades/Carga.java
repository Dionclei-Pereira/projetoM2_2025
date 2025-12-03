package entidades;

import enums.TipoCarga;

public class Carga {
    private TipoCarga tipoCarga;
    private int quantidadeKg;

    public Carga(TipoCarga tipoCarga, int quantidade) {
        this.tipoCarga = tipoCarga;
        this.quantidadeKg = quantidadeKg;
    }

    public TipoCarga getTipoCarga() {
        return this.tipoCarga;
    }

    public int getQuantidadeKg() {
        return this.quantidadeKg;
    }
}
