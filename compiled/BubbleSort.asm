	global main
extern printf
extern malloc
extern free
section .text

MainClass_BubbleSort:
main:
	push ebp
	mov ebp, esp
	mov eax, 10
	push eax
	mov eax, 16
	push eax
	call malloc
	add esp, 4
	push eax
	call BBS_Method_Start
	add esp, 8
	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_BBS:
BBS_Method_Start:
	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, dword [ebp + 12]
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BBS_Method_Init
	add esp, 8
	mov dword [ebp + -4], eax
	mov eax, dword [ebp + 8]
	push eax
	call BBS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, 99999
	push eax
	call PrintNum
	add esp, 4
	mov eax, dword [ebp + 8]
	push eax
	call BBS_Method_Sort
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, dword [ebp + 8]
	push eax
	call BBS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Sort:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	mov eax, 1
	push eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	mov eax, 1
	push eax
	mov eax, 0
	pop edx
	sub eax, edx
	mov dword [ebp + -12], eax
while_0_start:
	mov eax, dword [ebp + -8]
	push eax
	mov eax, dword [ebp + -12]
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
	mov eax, 1
	mov dword [ebp + -32], eax
while_1_start:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	push eax
	mov eax, dword [ebp + -32]
	pop edx
	cmp eax, edx
	jl ift_1
	mov eax, 0
	jmp ifend_1
ift_1:
	mov eax, 1
ifend_1:
	cmp eax, 0
	jg while_1_true
	jmp while_1_end
while_1_true:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -32]
	pop edx
	sub eax, edx
	mov dword [ebp + -28], eax
	mov eax, dword [ebp + -28]
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -16], eax
	mov eax, dword [ebp + -32]
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -20], eax
	mov eax, dword [ebp + -16]
	push eax
	mov eax, dword [ebp + -20]
	pop edx
	cmp eax, edx
	jl ift_2
	mov eax, 0
	jmp ifend_2
ift_2:
	mov eax, 1
ifend_2:
	cmp eax, 0
	jg if_2_true
if_2_false:
	mov eax, 0
	mov dword [ebp + -4], eax
	jmp if_2_end
if_2_true:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -32]
	pop edx
	sub eax, edx
	mov dword [ebp + -24], eax
	mov eax, dword [ebp + -24]
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -36], eax
	mov eax, dword [ebp + -24]
	push eax
	mov eax, dword [ebp + -32]
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword [ebp + -32]
	push eax
	mov eax, dword [ebp + -36]
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
if_2_end:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -32]
	pop edx
	add eax, edx
	mov dword [ebp + -32], eax
	jmp while_1_start
while_1_end:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	jmp while_0_start
while_0_end:
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Print:
	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, 0
	mov dword [ebp + -4], eax
while_3_start:
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_3
	mov eax, 0
	jmp ifend_3
ift_3:
	mov eax, 1
ifend_3:
	cmp eax, 0
	jg while_3_true
	jmp while_3_end
while_3_true:
	mov eax, dword [ebp + -4]
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	push eax
	call PrintNum
	add esp, 4
	mov eax, 1
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	jmp while_3_start
while_3_end:
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Init:
	push ebp
	mov ebp, esp
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
	mov eax, dword [ebp + 12]
	push eax
	add eax, 1
	mov edx, 4
	imul edx
	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 4], eax
	mov eax, 0
	push eax
	mov eax, 20
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 1
	push eax
	mov eax, 7
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 2
	push eax
	mov eax, 12
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 3
	push eax
	mov eax, 18
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 4
	push eax
	mov eax, 2
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 5
	push eax
	mov eax, 11
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 6
	push eax
	mov eax, 6
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 7
	push eax
	mov eax, 9
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 8
	push eax
	mov eax, 19
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 9
	push eax
	mov eax, 5
	pop ecx
	push eax
	mov eax, ecx
	add eax, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, 0
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

