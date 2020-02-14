	global main
extern printf
extern malloc
extern free
section .text

MainClass_LinkedList:
main:
 	push ebp
	mov ebp, esp
	mov eax, dword 8
 	push eax
	call malloc
	add esp, 4
 	push eax
	call LL_Method_Start
	add esp, 4
 	push eax
	call PrintNum
	add esp, 4
	mov esp, ebp
	pop ebp
	ret
Class_Element:
Element_Method_Init:
 	push ebp
	mov ebp, esp
	mov eax, dword [ebp + 20]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 4], eax
	mov eax, dword [ebp + 16]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 12], eax
	mov eax, dword 1
	mov esp, ebp
	pop ebp
	ret
Element_Method_GetAge:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 4]
	mov esp, ebp
	pop ebp
	ret
Element_Method_GetSalary:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	mov esp, ebp
	pop ebp
	ret
Element_Method_GetMarried:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	mov esp, ebp
	pop ebp
	ret
Element_Method_Equal:
 	push ebp
	mov ebp, esp
	sub esp, 16
	mov eax, dword 1
	mov dword [ebp + -4], eax
 	push dword [ebp + 12]
	call Element_Method_GetAge
	add esp, 4
	mov dword [ebp + -8], eax
 	push eax
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 4]
 	push dword [ebp + 8]
	call Element_Method_Compare
	add esp, 12
	cmp eax, 0
	je ift_0
	mov eax, dword 0
	jmp ifend_0
ift_0:
	mov eax, dword 1
ifend_0:
	cmp eax, 0
	jg if_0_true
if_0_false:
 	push dword [ebp + 12]
	call Element_Method_GetSalary
	add esp, 4
	mov dword [ebp + -12], eax
 	push eax
 	mov ebx, dword [ebp + 8]
	push dword [ebx + 8]
 	push dword [ebp + 8]
	call Element_Method_Compare
	add esp, 12
	cmp eax, 0
	je ift_1
	mov eax, dword 0
	jmp ifend_1
ift_1:
	mov eax, dword 1
ifend_1:
	cmp eax, 0
	jg if_1_true
if_1_false:
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	cmp eax, 0
	jg if_2_true
if_2_false:
 	push dword [ebp + 12]
	call Element_Method_GetMarried
	add esp, 4
	cmp eax, 0
	jg if_3_true
if_3_false:
	mov eax, dword 0
	mov dword [ebp + -16], eax
	jmp if_3_end
if_3_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_3_end:
	jmp if_2_end
if_2_true:
 	push dword [ebp + 12]
	call Element_Method_GetMarried
	add esp, 4
	cmp eax, 0
	je ift_2
	mov eax, dword 0
	jmp ifend_2
ift_2:
	mov eax, dword 1
ifend_2:
	cmp eax, 0
	jg if_4_true
if_4_false:
	mov eax, dword 0
	mov dword [ebp + -16], eax
	jmp if_4_end
if_4_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_4_end:
if_2_end:
	jmp if_1_end
if_1_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_1_end:
	jmp if_0_end
if_0_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_0_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
Element_Method_Compare:
 	push ebp
	mov ebp, esp
	sub esp, 8
	mov eax, dword 0
	mov dword [ebp + -4], eax
	mov eax, dword 1
 	push eax
	mov eax, dword [ebp + 12]
	pop edx
	add eax, edx
	mov dword [ebp + -8], eax
 	push dword [ebp + 12]
	mov eax, dword [ebp + 16]
	pop edx
	cmp eax, edx
	jl ift_3
	mov eax, dword 0
	jmp ifend_3
ift_3:
	mov eax, dword 1
ifend_3:
	cmp eax, 0
	jg if_5_true
if_5_false:
 	push dword [ebp + -8]
	mov eax, dword [ebp + 16]
	pop edx
	cmp eax, edx
	jl ift_4
	mov eax, dword 0
	jmp ifend_4
ift_4:
	mov eax, dword 1
ifend_4:
	cmp eax, 0
	je ift_5
	mov eax, dword 0
	jmp ifend_5
ift_5:
	mov eax, dword 1
ifend_5:
	cmp eax, 0
	jg if_6_true
if_6_false:
	mov eax, dword 1
	mov dword [ebp + -4], eax
	jmp if_6_end
if_6_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_6_end:
	jmp if_5_end
if_5_true:
	mov eax, dword 0
	mov dword [ebp + -4], eax
if_5_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
Class_List:
List_Method_Init:
 	push ebp
	mov ebp, esp
	mov eax, dword 1
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 12], eax
	mov eax, dword 1
	mov esp, ebp
	pop ebp
	ret
List_Method_InitNew:
 	push ebp
	mov ebp, esp
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 12], eax
	mov eax, dword [ebp + 20]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 4], eax
	mov eax, dword [ebp + 16]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
	mov eax, dword 1
	mov esp, ebp
	pop ebp
	ret
List_Method_Insert:
 	push ebp
	mov ebp, esp
	sub esp, 12
	mov eax, dword [ebp + 8]
	mov dword [ebp + -8], eax
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -12], eax
 	push dword [ebp + 12]
 	push dword [ebp + -8]
	mov eax, dword 0
 	push eax
 	push dword [ebp + -12]
	call List_Method_InitNew
	add esp, 16
	mov dword [ebp + -4], eax
	mov eax, dword [ebp + -12]
	mov esp, ebp
	pop ebp
	ret
List_Method_SetNext:
 	push ebp
	mov ebp, esp
	mov eax, dword [ebp + 12]
	mov ebx, dword [ebp + 8]
	mov dword [ebx + 8], eax
	mov eax, dword 1
	mov esp, ebp
	pop ebp
	ret
List_Method_Delete:
 	push ebp
	mov ebp, esp
	sub esp, 36
	mov eax, dword [ebp + 8]
	mov dword [ebp + -4], eax
	mov eax, dword 0
	mov dword [ebp + -8], eax
	mov eax, dword 1
 	push eax
	mov eax, dword 0
	pop edx
	sub eax, edx
	mov dword [ebp + -32], eax
	mov eax, dword [ebp + 8]
	mov dword [ebp + -16], eax
	mov eax, dword [ebp + 8]
	mov dword [ebp + -20], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	mov dword [ebp + -24], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 4]
	mov dword [ebp + -28], eax
while_7_start:
	mov eax, dword [ebp + -8]
	cmp eax, 0
	je ift_6
	mov eax, dword 0
	jmp ifend_6
ift_6:
	mov eax, dword 1
ifend_6:
 	push eax
	mov eax, dword [ebp + -24]
	cmp eax, 0
	je ift_7
	mov eax, dword 0
	jmp ifend_7
ift_7:
	mov eax, dword 1
ifend_7:
	pop edx
	cmp eax, edx
	je ift_8
	mov eax, dword 0
	jmp ifend_8
ift_8:
	mov eax, dword 1
ifend_8:
	cmp eax, 0
	jg while_7_true
	jmp while_7_end
while_7_true:
 	push dword [ebp + -28]
 	push dword [ebp + 12]
	call Element_Method_Equal
	add esp, 8
	cmp eax, 0
	jg if_8_true
if_8_false:
	mov eax, dword 0
	mov dword [ebp + -36], eax
	jmp if_8_end
if_8_true:
	mov eax, dword 1
	mov dword [ebp + -8], eax
	mov eax, dword 0
 	push eax
	mov eax, dword [ebp + -32]
	pop edx
	cmp eax, edx
	jl ift_9
	mov eax, dword 0
	jmp ifend_9
ift_9:
	mov eax, dword 1
ifend_9:
	cmp eax, 0
	jg if_9_true
if_9_false:
	mov eax, dword 555
 	push eax
	mov eax, dword 0
	pop edx
	sub eax, edx
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + -16]
	call List_Method_GetNext
	add esp, 4
 	push eax
 	push dword [ebp + -20]
	call List_Method_SetNext
	add esp, 8
	mov dword [ebp + -12], eax
	mov eax, dword 555
 	push eax
	mov eax, dword 0
	pop edx
	sub eax, edx
 	push eax
	call PrintNum
	add esp, 4
	jmp if_9_end
if_9_true:
 	push dword [ebp + -16]
	call List_Method_GetNext
	add esp, 4
	mov dword [ebp + -4], eax
if_9_end:
if_8_end:
	mov eax, dword [ebp + -8]
	cmp eax, 0
	je ift_10
	mov eax, dword 0
	jmp ifend_10
ift_10:
	mov eax, dword 1
ifend_10:
	cmp eax, 0
	jg if_10_true
if_10_false:
	mov eax, dword 0
	mov dword [ebp + -36], eax
	jmp if_10_end
if_10_true:
	mov eax, dword [ebp + -16]
	mov dword [ebp + -20], eax
 	push dword [ebp + -16]
	call List_Method_GetNext
	add esp, 4
	mov dword [ebp + -16], eax
 	push eax
	call List_Method_GetEnd
	add esp, 4
	mov dword [ebp + -24], eax
 	push dword [ebp + -16]
	call List_Method_GetElem
	add esp, 4
	mov dword [ebp + -28], eax
	mov eax, dword 1
	mov dword [ebp + -32], eax
if_10_end:
	jmp while_7_start
while_7_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
List_Method_Search:
 	push ebp
	mov ebp, esp
	sub esp, 20
	mov eax, dword 0
	mov dword [ebp + -4], eax
	mov eax, dword [ebp + 8]
	mov dword [ebp + -8], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	mov dword [ebp + -16], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 4]
	mov dword [ebp + -12], eax
while_11_start:
	mov eax, dword [ebp + -16]
	cmp eax, 0
	je ift_11
	mov eax, dword 0
	jmp ifend_11
ift_11:
	mov eax, dword 1
ifend_11:
	cmp eax, 0
	jg while_11_true
	jmp while_11_end
while_11_true:
 	push dword [ebp + -12]
 	push dword [ebp + 12]
	call Element_Method_Equal
	add esp, 8
	cmp eax, 0
	jg if_12_true
if_12_false:
	mov eax, dword 0
	mov dword [ebp + -20], eax
	jmp if_12_end
if_12_true:
	mov eax, dword 1
	mov dword [ebp + -4], eax
if_12_end:
 	push dword [ebp + -8]
	call List_Method_GetNext
	add esp, 4
	mov dword [ebp + -8], eax
 	push eax
	call List_Method_GetEnd
	add esp, 4
	mov dword [ebp + -16], eax
 	push dword [ebp + -8]
	call List_Method_GetElem
	add esp, 4
	mov dword [ebp + -12], eax
	jmp while_11_start
while_11_end:
	mov eax, dword [ebp + -4]
	mov esp, ebp
	pop ebp
	ret
List_Method_GetEnd:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	mov esp, ebp
	pop ebp
	ret
List_Method_GetElem:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 4]
	mov esp, ebp
	pop ebp
	ret
List_Method_GetNext:
 	push ebp
	mov ebp, esp
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 8]
	mov esp, ebp
	pop ebp
	ret
List_Method_Print:
 	push ebp
	mov ebp, esp
	sub esp, 12
	mov eax, dword [ebp + 8]
	mov dword [ebp + -4], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 12]
	mov dword [ebp + -8], eax
	mov ebx, dword [ebp + 8]
	mov eax, dword [ebx + 4]
	mov dword [ebp + -12], eax
while_13_start:
	mov eax, dword [ebp + -8]
	cmp eax, 0
	je ift_12
	mov eax, dword 0
	jmp ifend_12
ift_12:
	mov eax, dword 1
ifend_12:
	cmp eax, 0
	jg while_13_true
	jmp while_13_end
while_13_true:
 	push dword [ebp + -12]
	call Element_Method_GetAge
	add esp, 4
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + -4]
	call List_Method_GetNext
	add esp, 4
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_GetEnd
	add esp, 4
	mov dword [ebp + -8], eax
 	push dword [ebp + -4]
	call List_Method_GetElem
	add esp, 4
	mov dword [ebp + -12], eax
	jmp while_13_start
while_13_end:
	mov eax, dword 1
	mov esp, ebp
	pop ebp
	ret
Class_LL:
LL_Method_Start:
 	push ebp
	mov ebp, esp
	sub esp, 24
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -8], eax
 	push eax
	call List_Method_Init
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword [ebp + -8]
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Init
	add esp, 4
	mov dword [ebp + -12], eax
 	push dword [ebp + -4]
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -16], eax
	mov eax, dword 25
 	push eax
	mov eax, dword 37000
 	push eax
	mov eax, dword 0
 	push eax
 	push dword [ebp + -16]
	call Element_Method_Init
	add esp, 16
	mov dword [ebp + -12], eax
 	push dword [ebp + -16]
 	push dword [ebp + -4]
	call List_Method_Insert
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 10000000
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -16], eax
	mov eax, dword 39
 	push eax
	mov eax, dword 42000
 	push eax
	mov eax, dword 1
 	push eax
 	push dword [ebp + -16]
	call Element_Method_Init
	add esp, 16
	mov dword [ebp + -12], eax
	mov eax, dword [ebp + -16]
	mov dword [ebp + -20], eax
 	push dword [ebp + -16]
 	push dword [ebp + -4]
	call List_Method_Insert
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 10000000
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -16], eax
	mov eax, dword 22
 	push eax
	mov eax, dword 34000
 	push eax
	mov eax, dword 0
 	push eax
 	push dword [ebp + -16]
	call Element_Method_Init
	add esp, 16
	mov dword [ebp + -12], eax
 	push dword [ebp + -16]
 	push dword [ebp + -4]
	call List_Method_Insert
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -24], eax
	mov eax, dword 27
 	push eax
	mov eax, dword 34000
 	push eax
	mov eax, dword 0
 	push eax
 	push dword [ebp + -24]
	call Element_Method_Init
	add esp, 16
	mov dword [ebp + -12], eax
 	push dword [ebp + -20]
 	push dword [ebp + -4]
	call List_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + -24]
 	push dword [ebp + -4]
	call List_Method_Search
	add esp, 8
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 10000000
 	push eax
	call PrintNum
	add esp, 4
	mov eax, dword 20
 	push eax
	call malloc
	add esp, 4
	mov dword [ebp + -16], eax
	mov eax, dword 28
 	push eax
	mov eax, dword 35000
 	push eax
	mov eax, dword 0
 	push eax
 	push dword [ebp + -16]
	call Element_Method_Init
	add esp, 16
	mov dword [ebp + -12], eax
 	push dword [ebp + -16]
 	push dword [ebp + -4]
	call List_Method_Insert
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 2220000
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + -20]
 	push dword [ebp + -4]
	call List_Method_Delete
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 33300000
 	push eax
	call PrintNum
	add esp, 4
 	push dword [ebp + -16]
 	push dword [ebp + -4]
	call List_Method_Delete
	add esp, 8
	mov dword [ebp + -4], eax
 	push eax
	call List_Method_Print
	add esp, 4
	mov dword [ebp + -12], eax
	mov eax, dword 44440000
 	push eax
	call PrintNum
	add esp, 4
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

