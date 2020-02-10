	mov eax, [ebp + 12] 
	push eax
	add eax, 1
	mov ebx, 4
	imul ebx
	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx 
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
	pop eax
	mov esp, ebp 
	pop ebp
	ret
Class_BBS:
BBS_Method_Start:
	push ebp
	mov ebp, esp 
	sub esp, 4
	mov eax, [ebp + 12] 
	push eax
	mov eax, [ebp + 8] 
	push eax
	call BBS_Method_Init
	add esp, 8
	mov [ebp + -4], eax 
	mov eax, [ebp + 8] 
	push eax
	call BBS_Method_Print
	add esp, 4
	mov [ebp + -4], eax 
	mov eax, 99999
	push eax
	call PrintNum
	pop eax
	mov eax, [ebp + 8] 
	push eax
	call BBS_Method_Sort
	add esp, 4
	mov [ebp + -4], eax 
	mov eax, [ebp + 8] 
	push eax
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
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	pop edx
	sub eax, edx
	mov [ebp + -8], eax 
	mov eax, 1
	push eax
	mov eax, 0
	pop edx
	sub eax, edx
	mov [ebp + -12], eax 
while_0_start:
	mov eax, [ebp + -8] 
	push eax
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
	mov eax, 1
	push eax
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
	mov eax, 1
	push eax
	mov eax, [ebp + -32] 
	pop edx
	sub eax, edx
	mov [ebp + -28], eax 
	mov eax, [ebp + -28] 
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
	mov eax, [ebp + -16] 
	push eax
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
	mov eax, 1
	push eax
	mov eax, [ebp + -32] 
	pop edx
	sub eax, edx
	mov [ebp + -24], eax 
	mov eax, [ebp + -24] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov eax, [ecx + eax]
	mov [ebp + -36], eax 
	mov eax, [ebp + -24] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + -32] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov eax, [ecx + eax]
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, [ebp + -32] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + -36] 
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
if_2_end:
	mov eax, 1
	push eax
	mov eax, [ebp + -32] 
	pop edx
	add eax, edx
	mov [ebp + -32], eax 
	jmp while_1_start
while_1_end:
	mov eax, 1
	push eax
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
	mov eax, [eax + 8] 
	push eax
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
	mov eax, [ecx + eax]
	push eax
	call PrintNum
	pop eax
	mov eax, 1
	push eax
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
	mov eax, [ebp + 12] 
	push eax
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
	mov eax, 0
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 20
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 1
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 7
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 2
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 12
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 3
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 18
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 4
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 2
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 5
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 11
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 6
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 6
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 7
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 9
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 8
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 19
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 9
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, 5
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
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

