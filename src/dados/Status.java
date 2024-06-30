package src.dados;

public enum Status {
        CADASTRADA("Cadastrada"),
        EXECUTANDO("Executando"),
        FINALIZADA("Finalizada"),
        CANCELADA("Cancelada");

        private final String nome;

        private Status(String nome) {
            this.nome = nome;
        }

        public String getNome() {
            return nome;
        }
}
