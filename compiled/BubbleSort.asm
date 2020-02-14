	global main
extern printf
extern malloc
extern free
section .text

MainClass_BubbleSort:
main:
 	push ebp
	mov ebp, esp
	mov eax, dword 10
 	push eax
	mov eax, dword 16
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
 	push dword [ebp + 12]
 	push dword [ebp + 8]
	call BBS_Method_Init
	add esp, 8
	mov dword [ebp + -4], eax
 	push dword [ebp + 8]
	call BBS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, dword 99999
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + 8]
	call BBS_Method_Sort
	add esp, 4
	mov dword [ebp + -4], eax
 	push dword [ebp + 8]
	call BBS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Sort:
 	push ebp
	mov ebp, esp
	sub esp, 36
	mov eax, dword 1
 	push eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	mov eax, dword 1
 	push eax
	mov eax, dword 0
	pop edx
	sub eax, edx
	mov dword [ebp + -12], eax
while_0_start:
 	push dword [ebp + -8]
	mov eax, dword [ebp + -12]
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
	mov eax, dword 1
	mov dword [ebp + -32], eax
while_1_start:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
 	push eax
	mov eax, dword [ebp + -32]
	pop edx
	cmp eax, edx
	jl ift_1
	mov eax, dword 0
	jmp ifend_1
ift_1:
	mov eax, dword 1
ifend_1:
	cmp eax, 0
	jg while_1_true
	jmp while_1_end
while_1_true:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -32]
	pop edx
	sub eax, edx
	mov dword [ebp + -28], eax
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -16], eax
	mov ecx, dword [ebp + -32]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -20], eax
 	push dword [ebp + -16]
	mov eax, dword [ebp + -20]
	pop edx
	cmp eax, edx
	jl ift_2
	mov eax, dword 0
	jmp ifend_2
ift_2:
	mov eax, dword 1
ifend_2:
	cmp eax, 0
	jg if_2_true
if_2_false:
	mov eax, dword 0
	mov dword [ebp + -4], eax
	jmp if_2_end
if_2_true:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -32]
	pop edx
	sub eax, edx
	mov dword [ebp + -24], eax
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -36], eax
 	push dword [ebp + -24]
	mov ecx, dword [ebp + -32]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
 	push dword [ebp + -32]
	mov eax, dword [ebp + -36]
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
if_2_end:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -32]
	pop edx
	add eax, edx
	mov dword [ebp + -32], eax
	jmp while_1_start
while_1_end:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	jmp while_0_start
while_0_end:
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Print:
 	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, dword 0
	mov dword [ebp + -4], eax
while_3_start:
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_3
	mov eax, dword 0
	jmp ifend_3
ift_3:
	mov eax, dword 1
ifend_3:
	cmp eax, 0
	jg while_3_true
	jmp while_3_end
while_3_true:
	mov ecx, dword [ebp + -4]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	jmp while_3_start
while_3_end:
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Init:
 	push ebp
	mov ebp, esp
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
 	push dword [ebp + 12]
	add eax, 1
	mov edx, dword 4
	imul edx
 	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 4], eax
	mov eax, dword 0
 	push eax
	mov eax, dword 20
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 1
 	push eax
	mov eax, dword 7
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 2
 	push eax
	mov eax, dword 12
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 3
 	push eax
	mov eax, dword 18
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 4
 	push eax
	mov eax, dword 2
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 5
 	push eax
	mov eax, dword 11
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 6
 	push eax
	mov eax, dword 6
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 7
 	push eax
	mov eax, dword 9
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 8
 	push eax
	mov eax, dword 19
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
	mov eax, dword 9
 	push eax
	mov eax, dword 5
	pop ecx
 	push eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + ecx], eax
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

