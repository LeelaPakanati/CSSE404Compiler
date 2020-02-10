	global main
extern printf
extern malloc
extern free
section .text

MainClass_Test:
main:
	push ebp
	mov ebp, esp 
	mov eax, 6
	push eax
	mov eax, 30
	pop ebx
	push edx
	mov edx, 0
	div ebx
	pop edx
	push eax
	mov eax, 3
	pop edx
	add eax, edx
	push eax
	call PrintNum
	pop eax
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

