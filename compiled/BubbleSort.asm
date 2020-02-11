	global main
extern printf
extern malloc
extern free
section .text

MainClass_BubbleSort:
main:
	push ebp
	mov ebp, esp
	push 10
	push 16
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
	mov [ebp + -4], eax
	push dword [ebp + 8]
	call BBS_Method_Print
	add esp, 4
	mov [ebp + -4], eax
	push 99999
	call PrintNum
	add esp, 4
	push dword [ebp + 8]
	call BBS_Method_Sort
	add esp, 4
	mov [ebp + -4], eax
	push dword [ebp + 8]
	call BBS_Method_Print
	add esp, 4
	mov [ebp + -4], eax
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Sort:
	push ebp
	mov ebp, esp
	sub esp, 36
	push 1
	mov eax, [ebp + 8]
	mov eax, [eax + 8]
	pop edx
	sub eax, edx
	mov [ebp + -8], eax
	push 1
	mov eax, 0
	pop edx
	sub eax, edx
	mov [ebp + -12], eax
while_0_start:
	push dword [ebp + -8]
	mov eax, [ebp + -12]
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
	mov [ebp + -32], eax
while_1_start:
	push 1
	mov eax, [ebp + -8]
	pop edx
	add eax, edx
	push eax
	mov eax, [ebp + -32]
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
	push 1
	mov eax, [ebp + -32]
	pop edx
	sub eax, edx
	mov [ebp + -28], eax
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	mov eax, [ecx + eax]
	mov [ebp + -16], eax
	mov eax, [ebp + -32]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	mov eax, [ecx + eax]
	mov [ebp + -20], eax
	push dword [ebp + -16]
	mov eax, [ebp + -20]
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
	mov [ebp + -4], eax
	jmp if_2_end
if_2_true:
	push 1
	mov eax, [ebp + -32]
	pop edx
	sub eax, edx
	mov [ebp + -24], eax
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	mov eax, [ecx + eax]
	mov [ebp + -36], eax
	mov eax, [ebp + -32]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	push dword [ecx + eax]
	mov eax, [ebp + -24]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push dword [ebp + -36]
	mov eax, [ebp + -32]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
if_2_end:
	push 1
	mov eax, [ebp + -32]
	pop edx
	add eax, edx
	mov [ebp + -32], eax
	jmp while_1_start
while_1_end:
	push 1
	mov eax, [ebp + -8]
	pop edx
	sub eax, edx
	mov [ebp + -8], eax
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
	mov [ebp + -4], eax
while_3_start:
	mov eax, [ebp + 8]
	push dword [eax + 8]
	mov eax, [ebp + -4]
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
	mov eax, [ebp + -4]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	push dword [ecx + eax]
	call PrintNum
	add esp, 4
	push 1
	mov eax, [ebp + -4]
	pop edx
	add eax, edx
	mov [ebp + -4], eax
	jmp while_3_start
while_3_end:
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BBS_Method_Init:
	push ebp
	mov ebp, esp
	mov eax, [ebp + 12]
	mov ecx, [ebp + 8]
	mov [ecx + 8], eax
	push dword [ebp + 12]
	add eax, 1
	mov ebx, 4
	imul ebx
	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov ecx, [ebp + 8]
	mov [ecx + 4], eax
	push 20
	mov eax, 0
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 7
	mov eax, 1
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 12
	mov eax, 2
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 18
	mov eax, 3
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 2
	mov eax, 4
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 11
	mov eax, 5
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 6
	mov eax, 6
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 9
	mov eax, 7
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 19
	mov eax, 8
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 5
	mov eax, 9
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
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
	mov eax, [ebp + 8]
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

