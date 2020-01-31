package arch;

public enum Register{
	AX("%ax"),
	CX("%cx"),
	DX("%dx"),
	BX("%bx"),
	SP("%sp"),
	bp("%bp"),
	si("%si"),
	di("%di"),
	PRINT("PRINTOUT"),
	ARGS("INPUT ARGS");
	
	public String label;

    private Register(String label) {
        this.label = label;
    }
}
