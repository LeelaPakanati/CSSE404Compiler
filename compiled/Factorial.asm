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
	mov eax, 8
	push eax
	call malloc
	add esp, 4
	push eax
	call Fac_Method_ComputeFac
	add esp, 8
	push eax
	call PrintNum
	add esp, 4
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
	mov eax, dword [ebp + 12]
	pop edx
	cmp eax, edx
	jl ift_0
	mov eax, 0
	jmp ifend_0
ift_0:
	mov eax, 1
ifend_0:
	cmp eax, 0
	jg if_0_true
if_0_false:
	mov eax, 1
	push eax
	mov eax, dword [ebp + 12]
	pop edx
	sub eax, edx
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call Fac_Method_ComputeFac
	add esp, 8
	push eax
	mov eax, dword [ebp + 12]
	pop edx
	imul edx
	mov dword [ebp + -4], eax
	jmp if_0_end
if_0_true:
	mov eax, 1
	mov dword [ebp + -4], eax
if_0_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
Syscall_EXIT:
	mov eax, 1
	int 0x80

PrintNum:
	push ebp
	mov ebp, esp
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

