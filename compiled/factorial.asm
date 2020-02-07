	global main
extern printf
extern malloc
extern free
section .text

MainClass_Factorial:
main:
	push ebp
	mov ebp, esp 
	mov eax, 10
	push eax
	mov eax, 0
	push eax
	call malloc
	add esp, 4
	push eax
	call Fac_Method_ComputeFac
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov esp, ebp 
	pop ebp
	ret
Class_Fac:
Fac_Method_ComputeFac:
	push ebp
	mov ebp, esp 
	sub esp, 4
	mov eax, 1
	push eax
	mov eax, [ebp + 12] 
	pop edx
	cmp eax, edx
	jl if_0_true
if_0_false:
	mov eax, 1
	push eax
	mov eax, [ebp + 12] 
	pop edx
	sub eax, edx
	push eax
	mov eax, [ebp + 8] 
	push eax
	call Fac_Method_ComputeFac
	add esp, 8
	push eax
	mov eax, [ebp + 12] 
	pop ebx
	imul ebx
	mov [ebp + -4], eax 
	jmp if_0_end
if_0_true:
	mov eax, 1
	mov [ebp + -4], eax 
if_0_end:
	mov eax, [ebp + -4] 
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
