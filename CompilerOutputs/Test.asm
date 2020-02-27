	global main
extern printf
extern malloc
extern free
section .text

MainClass_Test:
main:
 	push ebp
	mov ebp, esp
	mov eax, dword 10000
 	push eax
	call PrintNum
	add esp, 4
	sub esp, 4
	mov eax, dword 10
	mov dword [ebp + -4], eax
	sub esp, 4
	mov eax, dword 2
	mov dword [ebp + -8], eax
	sub esp, 4
 	push dword [ebp + -4]
	add eax, 1
	mov edx, dword 4
	imul edx
 	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov dword [ebp + -12], eax
	mov eax, dword 0
 	push eax
	mov eax, dword 0
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 1
 	push eax
	mov eax, dword 1
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 2
 	push eax
	mov eax, dword 2
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 2
 	push eax
	mov eax, dword 1
	pop edx
	imul edx
 	push eax
	mov ecx, dword 1
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
	mov eax, dword 3
 	push eax
	mov eax, dword [ebp + -8]
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 4
 	push eax
	mov eax, dword 4
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 5
 	push eax
	mov eax, dword 1
 	push eax
	mov ecx, dword 4
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
	pop edx
	add eax, edx
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 6
 	push eax
	mov eax, dword 2
 	push eax
	mov ecx, dword 4
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
	pop edx
	add eax, edx
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 7
 	push eax
	mov eax, dword 5
 	push eax
	mov eax, dword 2
	pop edx
	add eax, edx
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 8
 	push eax
	mov eax, dword 4
 	push eax
	mov ecx, dword 2
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
	pop edx
	imul edx
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 9
 	push eax
	mov eax, dword 9
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 99999
 	push eax
	call PrintNum
	add esp, 4
	sub esp, 4
	mov eax, dword 0
	mov dword [ebp + -16], eax
while_0_start:
	mov eax, dword -1
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
 	push eax
	mov eax, dword [ebp + -16]
	pop edx
	cmp eax, edx
	jl ift_0
	mov eax, dword 0
	jmp ifend_0
ift_0:
	mov eax, dword 1
ifend_0:
	cmp eax, 0
	jg while_0_true
	jmp while_0_end
while_0_true:
 	push dword [ebp + -16]
	mov eax, dword [ebp + -16]
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	pop eax
	mov dword [ebx + ecx], eax
	mov ecx, dword [ebp + -16]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -16]
	pop edx
	add eax, edx
	mov dword [ebp + -16], eax
	jmp while_0_start
while_0_end:
	mov eax, dword -1
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + -12]
	mov eax, dword [ebx + ecx]
 	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_Test2:
Test2_Method_yeet:
 	push ebp
	mov ebp, esp
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
Syscall_EXIT:
	mov eax, dword 1
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

