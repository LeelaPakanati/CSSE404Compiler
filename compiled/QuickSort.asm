	global main
extern printf
extern malloc
extern free
section .text

MainClass_QuickSort:
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
	call QS_Method_Start
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_QS:
QS_Method_Start:
 	push ebp
	mov ebp, esp
	sub esp, 4
 	push dword [ebp + 12]
 	push dword [ebp + 8]
	call QS_Method_Init
	add esp, 8
	mov dword [ebp + -4], eax
 	push dword [ebp + 8]
	call QS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, dword 9999
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 1
 	push eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	pop edx
	sub eax, edx
	mov dword [ebp + -4], eax
	mov eax, dword 0
 	push eax
 	push dword [ebp + -4]
 	push dword [ebp + 8]
	call QS_Method_Sort
	add esp, 12
	mov dword [ebp + -4], eax
 	push dword [ebp + 8]
	call QS_Method_Print
	add esp, 4
	mov dword [ebp + -4], eax
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
QS_Method_Sort:
 	push ebp
	mov ebp, esp
	sub esp, 32
	mov eax, dword 0
	mov dword [ebp + -20], eax
 	push dword [ebp + 12]
	mov eax, dword [ebp + 16]
	pop edx
	cmp eax, edx
	jl ift_0
	mov eax, dword 0
	jmp ifend_0
ift_0:
	mov eax, dword 1
ifend_0:
	cmp eax, 0
	jg if_0_true
if_0_false:
	mov eax, dword 0
	mov dword [ebp + -16], eax
	jmp if_0_end
if_0_true:
	mov ecx, dword [ebp + 12]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -4], eax
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + 16]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	mov eax, dword [ebp + 12]
	mov dword [ebp + -12], eax
	mov eax, dword 1
	mov dword [ebp + -24], eax
while_1_start:
	mov eax, dword [ebp + -24]
	cmp eax, 0
	jg while_1_true
	jmp while_1_end
while_1_true:
	mov eax, dword 1
	mov dword [ebp + -28], eax
while_2_start:
	mov eax, dword [ebp + -28]
	cmp eax, 0
	jg while_2_true
	jmp while_2_end
while_2_true:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -32], eax
 	push dword [ebp + -4]
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
	je ift_2
	mov eax, dword 0
	jmp ifend_2
ift_2:
	mov eax, dword 1
ifend_2:
	cmp eax, 0
	jg if_3_true
if_3_false:
	mov eax, dword 1
	mov dword [ebp + -28], eax
	jmp if_3_end
if_3_true:
	mov eax, dword 0
	mov dword [ebp + -28], eax
if_3_end:
	jmp while_2_start
while_2_end:
	mov eax, dword 1
	mov dword [ebp + -28], eax
while_4_start:
	mov eax, dword [ebp + -28]
	cmp eax, 0
	jg while_4_true
	jmp while_4_end
while_4_true:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -12]
	pop edx
	sub eax, edx
	mov dword [ebp + -12], eax
	mov ecx, eax
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -32], eax
 	push eax
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
	je ift_4
	mov eax, dword 0
	jmp ifend_4
ift_4:
	mov eax, dword 1
ifend_4:
	cmp eax, 0
	jg if_5_true
if_5_false:
	mov eax, dword 1
	mov dword [ebp + -28], eax
	jmp if_5_end
if_5_true:
	mov eax, dword 0
	mov dword [ebp + -28], eax
if_5_end:
	jmp while_4_start
while_4_end:
	mov ecx, dword [ebp + -8]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -20], eax
 	push dword [ebp + -8]
	mov ecx, dword [ebp + -12]
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
 	push dword [ebp + -12]
	mov eax, dword [ebp + -20]
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
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
 	push eax
	mov eax, dword [ebp + -12]
	pop edx
	cmp eax, edx
	jl ift_5
	mov eax, dword 0
	jmp ifend_5
ift_5:
	mov eax, dword 1
ifend_5:
	cmp eax, 0
	jg if_6_true
if_6_false:
	mov eax, dword 1
	mov dword [ebp + -24], eax
	jmp if_6_end
if_6_true:
	mov eax, dword 0
	mov dword [ebp + -24], eax
if_6_end:
	jmp while_1_start
while_1_end:
 	push dword [ebp + -12]
	mov ecx, dword [ebp + -8]
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
 	push dword [ebp + -8]
	mov ecx, dword [ebp + 12]
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
 	push dword [ebp + 12]
	mov eax, dword [ebp + -20]
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
 	push dword [ebp + 16]
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
 	push eax
 	push dword [ebp + 8]
	call QS_Method_Sort
	add esp, 12
	mov dword [ebp + -16], eax
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
 	push eax
 	push dword [ebp + 12]
 	push dword [ebp + 8]
	call QS_Method_Sort
	add esp, 12
	mov dword [ebp + -16], eax
if_0_end:
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
QS_Method_Print:
 	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, dword 0
	mov dword [ebp + -4], eax
while_7_start:
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_6
	mov eax, dword 0
	jmp ifend_6
ift_6:
	mov eax, dword 1
ifend_6:
	cmp eax, 0
	jg while_7_true
	jmp while_7_end
while_7_true:
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
	jmp while_7_start
while_7_end:
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
QS_Method_Init:
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

