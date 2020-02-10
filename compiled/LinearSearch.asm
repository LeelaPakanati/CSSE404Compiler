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

MainClass_LinearSearch:
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
	call LS_Method_Start
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov esp, ebp 
	pop ebp
	ret
Class_LS:
LS_Method_Start:
	push ebp
	mov ebp, esp 
	sub esp, 4
	sub esp, 4
	mov eax, [ebp + 12] 
	push eax
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Init
	add esp, 8
	mov [ebp + -4], eax 
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Print
	add esp, 4
	mov [ebp + -8], eax 
	mov eax, 9999
	push eax
	call PrintNum
	pop eax
	mov eax, 8
	push eax
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Search
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov eax, 12
	push eax
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Search
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov eax, 17
	push eax
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Search
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov eax, 50
	push eax
	mov eax, [ebp + 8] 
	push eax
	call LS_Method_Search
	add esp, 8
	push eax
	call PrintNum
	pop eax
	mov eax, 55
	mov esp, ebp 
	pop ebp
	ret
LS_Method_Print:
	push ebp
	mov ebp, esp 
	sub esp, 4
	mov eax, 1
	mov [ebp + -4], eax 
while_0_start:
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	push eax
	mov eax, [ebp + -4] 
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
	jmp while_0_start
while_0_end:
	mov eax, 0
	mov esp, ebp 
	pop ebp
	ret
LS_Method_Search:
	push ebp
	mov ebp, esp 
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
	mov eax, 1
	mov [ebp + -4], eax 
	mov eax, 0
	mov [ebp + -8], eax 
	mov eax, 0
	mov [ebp + -12], eax 
while_1_start:
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	push eax
	mov eax, [ebp + -4] 
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
	mov eax, [ebp + -4] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov eax, [ecx + eax]
	mov [ebp + -16], eax 
	mov eax, 1
	push eax
	mov eax, [ebp + 12] 
	pop edx
	add eax, edx
	mov [ebp + -20], eax 
	mov eax, [ebp + 12] 
	push eax
	mov eax, [ebp + -16] 
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
	mov eax, [ebp + -20] 
	push eax
	mov eax, [ebp + -16] 
	pop edx
	cmp eax, edx
	jl ift_3
	mov eax, 0
	jmp ifend_3
ift_3:
	mov eax, 1
ifend_3:
	cmp eax, 0
	je ift_4
	mov eax, 0
	jmp ifend_4
ift_4:
	mov eax, 1
ifend_4:
	cmp eax, 0
	jg if_3_true
if_3_false:
	mov eax, 1
	mov [ebp + -8], eax 
	mov eax, 1
	mov [ebp + -12], eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	mov [ebp + -4], eax 
	jmp if_3_end
if_3_true:
	mov eax, 0
	mov [ebp + -24], eax 
if_3_end:
	jmp if_2_end
if_2_true:
	mov eax, 0
	mov [ebp + -24], eax 
if_2_end:
	mov eax, 1
	push eax
	mov eax, [ebp + -4] 
	pop edx
	add eax, edx
	mov [ebp + -4], eax 
	jmp while_1_start
while_1_end:
	mov eax, [ebp + -12] 
	mov esp, ebp 
	pop ebp
	ret
LS_Method_Init:
	push ebp
	mov ebp, esp 
	sub esp, 4
	sub esp, 4
	sub esp, 4
	sub esp, 4
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
	mov eax, 1
	mov [ebp + -4], eax 
	mov eax, 1
	push eax
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	pop edx
	add eax, edx
	mov [ebp + -8], eax 
while_4_start:
	mov eax, [ebp + 8] 
	mov eax, [eax + 8] 
	push eax
	mov eax, [ebp + -4] 
	pop edx
	cmp eax, edx
	jl ift_5
	mov eax, 0
	jmp ifend_5
ift_5:
	mov eax, 1
ifend_5:
	cmp eax, 0
	jg while_4_true
	jmp while_4_end
while_4_true:
	mov eax, [ebp + -4] 
	push eax
	mov eax, 2
	pop ebx
	imul ebx
	mov [ebp + -12], eax 
	mov eax, 3
	push eax
	mov eax, [ebp + -8] 
	pop edx
	sub eax, edx
	mov [ebp + -16], eax 
	mov eax, [ebp + -4] 
	add eax, 1
	mov ebx, 4
	imul ebx
	mov ecx, eax 
	mov eax, [ebp + -16] 
	push eax
	mov eax, [ebp + -12] 
	pop edx
	add eax, edx
	mov edx, eax 
	mov eax, [ebp + 8] 
	mov eax, [eax + 4] 
	mov [eax + ecx], edx
	mov eax, 1
	push eax
	mov eax, [ebp + -4] 
	pop edx
	add eax, edx
	mov [ebp + -4], eax 
	mov eax, 1
	push eax
	mov eax, [ebp + -8] 
	pop edx
	sub eax, edx
	mov [ebp + -8], eax 
	jmp while_4_start
while_4_end:
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

