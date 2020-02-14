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
	mov eax, 10
	mov dword [ebp + -4], eax
	sub esp, 4
	mov eax, dword [ebp + -4]
	push eax
	add eax, 1
	mov ebx, 4
	imul ebx
	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov dword [ebp + -8], eax
	mov eax, 0
	mov ecx, eax
	mov eax, 0
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 1
	mov ecx, eax
	mov eax, 1
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	sub esp, 4
	mov eax, 2
	mov dword [ebp + -12], eax
	mov eax, 2
	mov ecx, eax
	mov eax, dword [ebp + -12]
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 2
	push eax
	mov eax, 1
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	mov dword [ebp + -12], eax
	mov eax, 3
	mov ecx, eax
	mov eax, dword [ebp + -12]
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 4
	mov ecx, eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + -12]
	pop edx
	add eax, edx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 99999
	push eax
	call PrintNum
	add esp, 4
	mov eax, 5
	mov ecx, eax
	mov eax, 1
	push eax
	mov eax, 4
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 6
	mov ecx, eax
	mov eax, 2
	push eax
	mov eax, 4
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 7
	mov ecx, eax
	mov eax, 3
	push eax
	mov eax, 2
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	pop ebx
	imul ebx
	push eax
	mov eax, 1
	pop edx
	add eax, edx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 8
	mov ecx, eax
	mov eax, 4
	push eax
	mov eax, 2
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	pop ebx
	imul ebx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	mov eax, 9
	mov ecx, eax
	mov eax, 9
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	pop eax
	mov dword [ebp + -8], eax
	sub esp, 4
	mov eax, 0
	mov dword [ebp + -16], eax
while_0_start:
	mov ecx, 0
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	push eax
	mov eax, dword [ebp + -16]
	pop edx
	cmp eax, edx
	jl ift_0
	mov eax, 0
	jmp ifend_0
ift_0:
	mov eax, 1
ifend_0:
	cmp eax, 0
	jg while_0_true
	jmp while_0_end
while_0_true:
	mov eax, dword [ebp + -16]
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	push eax
	call PrintNum
	add esp, 4
	mov eax, 1
	push eax
	mov eax, dword [ebp + -16]
	pop edx
	add eax, edx
	mov dword [ebp + -16], eax
	jmp while_0_start
while_0_end:
	mov ecx, 0
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -8]
	mov eax, dword [ebp + -8]
	push eax
	call PrintNum
	add esp, 4
	sub esp, 4
	mov eax, 5
	mov dword [ebp + -20], eax
	sub esp, 4
	mov eax, 10
	mov dword [ebp + -24], eax
	mov eax, dword [ebp + -20]
	mov dword [ebp + -24], eax
	mov eax, dword [ebp + -24]
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
	mov eax, dword [ebp + 8]
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

