	global main
extern printf
extern malloc
extern free
section .text

MainClass_BinarySearch:
main:
	push ebp
	mov ebp, esp
	mov eax, 20
	push eax
	mov eax, 16
	push eax
	call malloc
	add esp, 4
	push eax
	call BS_Method_Start
	add esp, 8
	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_BS:
BS_Method_Start:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	mov eax, dword [ebp + 12]
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Init
	add esp, 8
	mov dword [ebp + -4], eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Print
	add esp, 4
	mov dword [ebp + -8], eax
	mov eax, 8
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_0_true
if_0_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_0_end
if_0_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_0_end:
	mov eax, 19
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_1_true
if_1_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_1_end
if_1_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_1_end:
	mov eax, 20
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_2_true
if_2_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_2_end
if_2_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_2_end:
	mov eax, 21
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_3_true
if_3_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_3_end
if_3_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_3_end:
	mov eax, 37
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_4_true
if_4_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_4_end
if_4_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_4_end:
	mov eax, 38
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_5_true
if_5_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_5_end
if_5_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_5_end:
	mov eax, 39
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_6_true
if_6_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_6_end
if_6_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_6_end:
	mov eax, 50
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_7_true
if_7_false:
	mov eax, 0
	push eax
	call PrintNum
	add esp, 4
	jmp if_7_end
if_7_true:
	mov eax, 1
	push eax
	call PrintNum
	add esp, 4
if_7_end:
	mov eax, 999
	mov esp, ebp
	pop ebp
	ret
BS_Method_Search:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	mov eax, 0
	mov dword [ebp + -24], eax
	mov eax, 0
	mov dword [ebp + -4], eax
	mov ecx, 0
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + 4]
	mov dword [ebp + -8], eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	mov eax, 0
	mov dword [ebp + -12], eax
	mov eax, 1
	mov dword [ebp + -16], eax
while_8_start:
	mov eax, dword [ebp + -16]
	cmp eax, 0
	jg while_8_true
	jmp while_8_end
while_8_true:
	mov eax, dword [ebp + -8]
	push eax
	mov eax, dword [ebp + -12]
	pop edx
	add eax, edx
	mov dword [ebp + -20], eax
	mov eax, dword [ebp + -20]
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Div
	add esp, 8
	mov dword [ebp + -20], eax
	mov eax, dword [ebp + -20]
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + 4]
	mov dword [ebp + -24], eax
	mov eax, dword [ebp + -24]
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
	jg if_9_true
if_9_false:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -20]
	pop edx
	add eax, edx
	mov dword [ebp + -12], eax
	jmp if_9_end
if_9_true:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -20]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
if_9_end:
	mov eax, dword [ebp + -24]
	push eax
	mov eax, dword [ebp + 12]
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Compare
	add esp, 12
	cmp eax, 0
	jg if_10_true
if_10_false:
	mov eax, 1
	mov dword [ebp + -16], eax
	jmp if_10_end
if_10_true:
	mov eax, 0
	mov dword [ebp + -16], eax
if_10_end:
	mov eax, dword [ebp + -12]
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	cmp eax, edx
	jl ift_1
	mov eax, 0
	jmp ifend_1
ift_1:
	mov eax, 1
ifend_1:
	cmp eax, 0
	jg if_11_true
if_11_false:
	mov eax, 0
	mov dword [ebp + -28], eax
	jmp if_11_end
if_11_true:
	mov eax, 0
	mov dword [ebp + -16], eax
if_11_end:
	jmp while_8_start
while_8_end:
	mov eax, dword [ebp + -24]
	push eax
	mov eax, dword [ebp + 12]
	push eax
	mov eax, dword [ebp + 8]
	push eax
	call BS_Method_Compare
	add esp, 12
	cmp eax, 0
	jg if_12_true
if_12_false:
	mov eax, 0
	mov dword [ebp + -4], eax
	jmp if_12_end
if_12_true:
	mov eax, 1
	mov dword [ebp + -4], eax
if_12_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Div:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	sub esp, 4
	mov eax, 0
	mov dword [ebp + -4], eax
	mov eax, 0
	mov dword [ebp + -8], eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + 12]
	pop edx
	sub eax, edx
	mov dword [ebp + -12], eax
while_13_start:
	mov eax, dword [ebp + -12]
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	cmp eax, edx
	jl ift_2
	mov eax, 0
	jmp ifend_2
ift_2:
	mov eax, 1
ifend_2:
	cmp eax, 0
	jg while_13_true
	jmp while_13_end
while_13_true:
	mov eax, 1
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	mov eax, 2
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
	jmp while_13_start
while_13_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Compare:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	mov eax, 0
	mov dword [ebp + -4], eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + 12]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
	mov eax, dword [ebp + 12]
	push eax
	mov eax, dword [ebp + 16]
	pop edx
	cmp eax, edx
	jl ift_3
	mov eax, 0
	jmp ifend_3
ift_3:
	mov eax, 1
ifend_3:
	cmp eax, 0
	jg if_14_true
if_14_false:
	mov eax, dword [ebp + -8]
	push eax
	mov eax, dword [ebp + 16]
	pop edx
	cmp eax, edx
	jl ift_4
	mov eax, 0
	jmp ifend_4
ift_4:
	mov eax, 1
ifend_4:
	cmp eax, 0
	je ift_5
	mov eax, 0
	jmp ifend_5
ift_5:
	mov eax, 1
ifend_5:
	cmp eax, 0
	jg if_15_true
if_15_false:
	mov eax, 1
	mov dword [ebp + -4], eax
	jmp if_15_end
if_15_true:
	mov eax, 0
	mov dword [ebp + -4], eax
if_15_end:
	jmp if_14_end
if_14_true:
	mov eax, 0
	mov dword [ebp + -4], eax
if_14_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Print:
	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, 1
	mov dword [ebp + -4], eax
while_16_start:
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_6
	mov eax, 0
	jmp ifend_6
ift_6:
	mov eax, 1
ifend_6:
	cmp eax, 0
	jg while_16_true
	jmp while_16_end
while_16_true:
	mov eax, dword [ebp + -4]
	mov ecx, eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + 4]
	push eax
	call PrintNum
	add esp, 4
	mov eax, 1
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	jmp while_16_start
while_16_end:
	mov eax, 99999
	push eax
	call PrintNum
	add esp, 4
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BS_Method_Init:
	push ebp
	mov ebp, esp
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
	mov eax, dword [ebp + 12]
	push eax
	add eax, 1
	mov ebx, 4
	imul ebx
	push eax
	call malloc
	add esp, 4
	pop ecx
	mov [eax + 0], ecx
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 4], eax
	mov eax, 1
	mov dword [ebp + -4], eax
	mov eax, 1
	push eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
while_17_start:
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_7
	mov eax, 0
	jmp ifend_7
ift_7:
	mov eax, 1
ifend_7:
	cmp eax, 0
	jg while_17_true
	jmp while_17_end
while_17_true:
	mov eax, dword [ebp + -4]
	push eax
	mov eax, 2
	pop ebx
	imul ebx
	mov dword [ebp + -16], eax
	mov eax, 3
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -12], eax
	mov eax, dword [ebp + -4]
	mov ecx, eax
	mov eax, dword [ebp + -12]
	push eax
	mov eax, dword [ebp + -16]
	pop edx
	add eax, edx
	push eax
	add ecx, 1
	mov edx, 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	pop eax
	mov dword [ebx + 4], eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	mov eax, 1
	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	jmp while_17_start
while_17_end:
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
	mov eax, dword [ebp + 8]
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

