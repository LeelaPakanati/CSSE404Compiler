	global main
extern printf
extern malloc
extern free
section .text

MainClass_LinearSearch:
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
	call LS_Method_Start
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_LS:
LS_Method_Start:
 	push ebp
	mov ebp, esp
	sub esp, 8
 	push dword [ebp + 12]
 	push dword [ebp + 8]
	call LS_Method_Init
	add esp, 8
	mov dword [ebp + -4], eax
 	push dword [ebp + 8]
	call LS_Method_Print
	add esp, 4
	mov dword [ebp + -8], eax
	mov eax, dword 9999
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 8
 	push eax
 	push dword [ebp + 8]
	call LS_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 12
 	push eax
 	push dword [ebp + 8]
	call LS_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 17
 	push eax
 	push dword [ebp + 8]
	call LS_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 50
 	push eax
 	push dword [ebp + 8]
	call LS_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 55
	mov esp, ebp
	pop ebp
	ret
LS_Method_Print:
 	push ebp
	mov ebp, esp
	sub esp, 4
	mov eax, dword 1
	mov dword [ebp + -4], eax
while_0_start:
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
	mov eax, dword [ebp + -4]
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
	jmp while_0_start
while_0_end:
	mov eax, dword 0
	mov esp, ebp
	pop ebp
	ret
LS_Method_Search:
 	push ebp
	mov ebp, esp
	sub esp, 24
	mov eax, dword 1
	mov dword [ebp + -4], eax
	mov eax, dword 0
	mov dword [ebp + -8], eax
	mov eax, dword 0
	mov dword [ebp + -12], eax
while_1_start:
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
	mov eax, dword [ebp + -4]
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
	mov ecx, dword [ebp + -4]
	mov eax, ecx
	add eax, 1
	mov edx, dword 4
	imul edx
	mov ecx, eax
	mov ebx, dword [ebp + 8]
	mov ebx, dword [ebx + 4]
	mov eax, dword [ebx + ecx]
	mov dword [ebp + -16], eax
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + 12]
	pop edx
	add eax, edx
	mov dword [ebp + -20], eax
 	push dword [ebp + 12]
	mov eax, dword [ebp + -16]
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
 	push dword [ebp + -20]
	mov eax, dword [ebp + -16]
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
	jg if_3_true
if_3_false:
	mov eax, dword 1
	mov dword [ebp + -8], eax
	mov eax, dword 1
	mov dword [ebp + -12], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	mov dword [ebp + -4], eax
	jmp if_3_end
if_3_true:
	mov eax, dword 0
	mov dword [ebp + -24], eax
if_3_end:
	jmp if_2_end
if_2_true:
	mov eax, dword 0
	mov dword [ebp + -24], eax
if_2_end:
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	jmp while_1_start
while_1_end:
	mov eax, dword [ebp + -12]
	mov esp, ebp
	pop ebp
	ret
LS_Method_Init:
 	push ebp
	mov ebp, esp
	sub esp, 16
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
	mov eax, dword 1
	mov dword [ebp + -4], eax
	mov eax, dword 1
 	push eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
while_4_start:
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
	mov eax, dword [ebp + -4]
	pop edx
	cmp eax, edx
	jl ift_5
	mov eax, dword 0
	jmp ifend_5
ift_5:
	mov eax, dword 1
ifend_5:
	cmp eax, 0
	jg while_4_true
	jmp while_4_end
while_4_true:
 	push dword [ebp + -4]
	mov eax, dword 2
	pop edx
	imul edx
	mov dword [ebp + -12], eax
	mov eax, dword 3
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -16], eax
 	push dword [ebp + -4]
 	push dword [ebp + -16]
	mov eax, dword [ebp + -12]
	pop edx
	add eax, edx
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
	mov eax, dword [ebp + -4]
	pop edx
	add eax, edx
	mov dword [ebp + -4], eax
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + -8]
	pop edx
	sub eax, edx
	mov dword [ebp + -8], eax
	jmp while_4_start
while_4_end:
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

