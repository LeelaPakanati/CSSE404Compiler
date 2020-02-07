package arch;

public enum SysCall{
	EXIT(1),
	FORK(2),
	READ(3),
	WRITE(4),
	OPEN(5),
	CLOSE(6);

	public int value;

    private SysCall(int value) {
        this.value = value;
    }
}
