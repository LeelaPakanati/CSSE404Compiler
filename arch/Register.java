package arch;

public enum Register{
	AX("%eax"),
	CX("%ecx"),
	DX("%edx"),
	BX("%ebx"),
	SP("%sp"),
	BP("%bp"),
	SI("%si"),
	DI("%di"),
	PRINT("PRINTOUT"),
	ARGS("INPUT ARGS");
	
	public String label;

    private Register(String label) {
        this.label = label;
    }
}
