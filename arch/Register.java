package arch;

public enum Register{
	AX("eax"),
	CX("ecx"),
	DX("edx"),
	BX("ebx"),
	SP("esp"),
	BP("ebp"),
	SI("esi"),
	DI("edi"),
	THIS("THIS"),
	CLASS("CLASS"),
	ERROR("ERROR"),
	ARGS("INPUT ARGS");
	
	public String label;

    private Register(String label) {
        this.label = label;
    }
}
