	global main
extern printf
extern malloc
extern free
section .text

MainClass_BinarySearch:
main:
	push ebp
	mov ebp, esp
	push 20
	push 16
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
	sub esp, 8
	push dword [ebp + 12]
	push dword [ebp + 8]
	call BS_Method_Init
	add esp, 8
	mov [ebp + -4], eax
	push dword [ebp + 8]
	call BS_Method_Print
	add esp, 4
	mov [ebp + -8], eax
	push 8
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_0_true
if_0_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_0_end
if_0_true:
	push 1
	call PrintNum
	add esp, 4
if_0_end:
	push 19
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_1_true
if_1_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_1_end
if_1_true:
	push 1
	call PrintNum
	add esp, 4
if_1_end:
	push 20
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_2_true
if_2_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_2_end
if_2_true:
	push 1
	call PrintNum
	add esp, 4
if_2_end:
	push 21
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_3_true
if_3_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_3_end
if_3_true:
	push 1
	call PrintNum
	add esp, 4
if_3_end:
	push 37
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_4_true
if_4_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_4_end
if_4_true:
	push 1
	call PrintNum
	add esp, 4
if_4_end:
	push 38
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_5_true
if_5_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_5_end
if_5_true:
	push 1
	call PrintNum
	add esp, 4
if_5_end:
	push 39
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_6_true
if_6_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_6_end
if_6_true:
	push 1
	call PrintNum
	add esp, 4
if_6_end:
	push 50
	push dword [ebp + 8]
	call BS_Method_Search
	add esp, 8
	cmp eax, 0
	jg if_7_true
if_7_false:
	push 0
	call PrintNum
	add esp, 4
	jmp if_7_end
if_7_true:
	push 1
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
	sub esp, 28
	mov eax, 0
	mov [ebp + -24], eax
	mov eax, 0
	mov [ebp + -4], eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	mov eax, [eax + 0]
	mov [ebp + -8], eax
	push 1
	mov eax, [ebp + -8]
	pop edx
	sub eax, edx
	mov [ebp + -8], eax
	mov eax, 0
	mov [ebp + -12], eax
	mov eax, 1
	mov [ebp + -16], eax
while_8_start:
	mov eax, [ebp + -16]
	cmp eax, 0
	jg while_8_true
	jmp while_8_end
while_8_true:
	push dword [ebp + -8]
	mov eax, [ebp + -12]
	pop edx
	add eax, edx
	mov [ebp + -20], eax
	push eax
	push dword [ebp + 8]
	call BS_Method_Div
	add esp, 8
	mov [ebp + -20], eax
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	mov eax, [ecx + eax]
	mov [ebp + -24], eax
	push eax
	mov eax, [ebp + 12]
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
	push 1
	mov eax, [ebp + -20]
	pop edx
	add eax, edx
	mov [ebp + -12], eax
	jmp if_9_end
if_9_true:
	push 1
	mov eax, [ebp + -20]
	pop edx
	sub eax, edx
	mov [ebp + -8], eax
if_9_end:
	push dword [ebp + -24]
	push dword [ebp + 12]
	push dword [ebp + 8]
	call BS_Method_Compare
	add esp, 12
	cmp eax, 0
	jg if_10_true
if_10_false:
	mov eax, 1
	mov [ebp + -16], eax
	jmp if_10_end
if_10_true:
	mov eax, 0
	mov [ebp + -16], eax
if_10_end:
	push dword [ebp + -12]
	mov eax, [ebp + -8]
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
	mov [ebp + -28], eax
	jmp if_11_end
if_11_true:
	mov eax, 0
	mov [ebp + -16], eax
if_11_end:
	jmp while_8_start
while_8_end:
	push dword [ebp + -24]
	push dword [ebp + 12]
	push dword [ebp + 8]
	call BS_Method_Compare
	add esp, 12
	cmp eax, 0
	jg if_12_true
if_12_false:
	mov eax, 0
	mov [ebp + -4], eax
	jmp if_12_end
if_12_true:
	mov eax, 1
	mov [ebp + -4], eax
if_12_end:
	mov eax, [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Div:
	push ebp
	mov ebp, esp
	sub esp, 12
	mov eax, 0
	mov [ebp + -4], eax
	mov eax, 0
	mov [ebp + -8], eax
	push 1
	mov eax, [ebp + 12]
	pop edx
	sub eax, edx
	mov [ebp + -12], eax
while_13_start:
	push dword [ebp + -12]
	mov eax, [ebp + -8]
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
	push 1
	mov eax, [ebp + -4]
	pop edx
	add eax, edx
	mov [ebp + -4], eax
	push 2
	mov eax, [ebp + -8]
	pop edx
	add eax, edx
	mov [ebp + -8], eax
	jmp while_13_start
while_13_end:
	mov eax, [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Compare:
	push ebp
	mov ebp, esp
	sub esp, 8
	mov eax, 0
	mov [ebp + -4], eax
	push 1
	mov eax, [ebp + 12]
	pop edx
	add eax, edx
	mov [ebp + -8], eax
	push dword [ebp + 12]
	mov eax, [ebp + 16]
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
	push dword [ebp + -8]
	mov eax, [ebp + 16]
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
	mov [ebp + -4], eax
	jmp if_15_end
if_15_true:
	mov eax, 0
	mov [ebp + -4], eax
if_15_end:
	jmp if_14_end
if_14_true:
	mov eax, 0
	mov [ebp + -4], eax
if_14_end:
	mov eax, [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
BS_Method_Print:
	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, 1
	mov [ebp + -4], eax
while_16_start:
	mov eax, [ebp + 8]
	push dword [eax + 8]
	mov eax, [ebp + -4]
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
	jmp while_16_start
while_16_end:
	push 99999
	call PrintNum
	add esp, 4
	mov eax, 0
	mov esp, ebp
	pop ebp
	ret
BS_Method_Init:
	push ebp
	mov ebp, esp
	sub esp, 16
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
	mov eax, 1
	mov [ebp + -4], eax
	push 1
	mov eax, [ebp + 8]
	mov eax, [eax + 8]
	pop edx
	add eax, edx
	mov [ebp + -8], eax
while_17_start:
	mov eax, [ebp + 8]
	push dword [eax + 8]
	mov eax, [ebp + -4]
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
	push dword [ebp + -4]
	mov eax, 2
	pop ebx
	imul ebx
	mov [ebp + -16], eax
	push 3
	mov eax, [ebp + -8]
	pop edx
	sub eax, edx
	mov [ebp + -12], eax
	push eax
	mov eax, [ebp + -16]
	pop edx
	add eax, edx
	push eax
	mov eax, [ebp + -4]
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax
	mov eax, [ebp + 8]
	mov eax, [eax + 4]
	pop edx
	mov [eax + ecx], edx
	push 1
	mov eax, [ebp + -4]
	pop edx
	add eax, edx
	mov [ebp + -4], eax
	push 1
	mov eax, [ebp + -8]
	pop edx
	sub eax, edx
	mov [ebp + -8], eax
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
	mov eax, [ebp + 8]
	push eax
	push message
	call printf
	mov esp, ebp
	pop ebp
	ret
message db "%d", 10, 0

