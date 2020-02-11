	global main
extern printf
extern malloc
extern free
section .text

MainClass_Test:
main:
	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, 5
	mov [ebp + -4], eax
	sub esp, 4
	mov eax, 10
	mov [ebp + -8], eax
	mov eax, [ebp + -4]
	mov [ebp + -8], eax
	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Syscall_EXIT:
	mov eax, 1
	int 0x80

PrintNum:
	push ebp
	mov ebp, esp
	mov eax, [ebp + 8]
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

