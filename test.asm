default rel






default rel

global _lib_newline
global _lib_printInt
global _lib_printlnInt
global _lib_print
global _lib_println
global _lib_getString
global _lib_getInt
global _lib_toString
global _lib_str_length
global _lib_str_ord
global _lib_str_substring
global _lib_str_parseInt
global _lib_strcat
global _lib_strcmp
global _lib_alloc

extern strcmp
extern strcat
extern strcpy
extern atol
extern strncpy
extern strlen
extern sprintf
extern __stack_chk_fail
extern __isoc99_scanf
extern malloc
extern puts
extern fputs
extern stdout
extern printf
extern putchar
extern _GLOBAL_OFFSET_TABLE_


SECTION .text

_lib_newline:
        push    rbp
        mov     rbp, rsp
        mov     edi, 10
        call    putchar
        nop
        pop     rbp
        ret


_lib_printInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_006]
        mov     eax, 0
        call    printf
        nop
        leave
        ret


_lib_printlnInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_007]
        mov     eax, 0
        call    printf
        nop
        leave
        ret


_lib_print:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rdx, qword [rel stdout]
        mov     rax, qword [rbp-8H]
        mov     rsi, rdx
        mov     rdi, rax
        call    fputs
        nop
        leave
        ret


_lib_println:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    puts
        nop
        leave
        ret


_lib_getString:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     edi, 256
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rax, qword [rbp-8H]
        mov     rsi, rax
        lea     rdi, [rel L_008]
        mov     eax, 0
        call    __isoc99_scanf
        mov     rax, qword [rbp-8H]
        leave
        ret


_lib_getInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16


        mov     rax, qword [fs:abs 28H]
        mov     qword [rbp-8H], rax
        xor     eax, eax
        mov     dword [rbp-0CH], 0
        lea     rax, [rbp-0CH]
        mov     rsi, rax
        lea     rdi, [rel L_006]
        mov     eax, 0
        call    __isoc99_scanf
        mov     eax, dword [rbp-0CH]
        mov     rdx, qword [rbp-8H]


        xor     rdx, qword [fs:abs 28H]
        jz      L_001
        call    __stack_chk_fail
L_001:  leave
        ret


_lib_toString:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 32
        mov     qword [rbp-18H], rdi
        mov     edi, 256
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rdx, qword [rbp-18H]
        mov     rax, qword [rbp-8H]
        lea     rsi, [rel L_006]
        mov     rdi, rax
        mov     eax, 0
        call    sprintf
        mov     rax, qword [rbp-8H]
        leave
        ret


_lib_str_length:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    strlen
        leave
        ret


_lib_str_ord:
        push    rbp
        mov     rbp, rsp
        mov     qword [rbp-8H], rdi
        mov     dword [rbp-0CH], esi
        mov     eax, dword [rbp-0CH]
        movsxd  rdx, eax
        mov     rax, qword [rbp-8H]
        add     rax, rdx
        movzx   eax, byte [rax]
        movsx   eax, al
        pop     rbp
        ret


_lib_str_substring:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 32
        mov     qword [rbp-18H], rdi
        mov     dword [rbp-1CH], esi
        mov     dword [rbp-20H], edx
        mov     eax, dword [rbp-20H]
        sub     eax, dword [rbp-1CH]
        add     eax, 1
        mov     dword [rbp-0CH], eax
        mov     eax, dword [rbp-0CH]
        add     eax, 1
        cdqe
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-8H], rax
        mov     eax, dword [rbp-0CH]
        movsxd  rdx, eax
        mov     eax, dword [rbp-1CH]
        movsxd  rcx, eax
        mov     rax, qword [rbp-18H]
        add     rcx, rax
        mov     rax, qword [rbp-8H]
        mov     rsi, rcx
        mov     rdi, rax
        call    strncpy
        mov     rax, qword [rbp-8H]
        leave
        ret


_lib_str_parseInt:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     rax, qword [rbp-8H]
        mov     rdi, rax
        call    atol
        leave
        ret


_lib_strcat:
        push    rbp
        mov     rbp, rsp
        push    rbx
        sub     rsp, 40
        mov     qword [rbp-28H], rdi
        mov     qword [rbp-30H], rsi
        mov     rax, qword [rbp-28H]
        mov     rdi, rax
        call    strlen
        mov     rbx, rax
        mov     rax, qword [rbp-30H]
        mov     rdi, rax
        call    strlen
        add     rax, rbx
        add     rax, 1
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-18H], rax
        mov     rdx, qword [rbp-28H]
        mov     rax, qword [rbp-18H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcpy
        mov     rdx, qword [rbp-30H]
        mov     rax, qword [rbp-18H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcat
        mov     rax, qword [rbp-18H]
        add     rsp, 40
        pop     rbx
        pop     rbp
        ret


_lib_strcmp:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 16
        mov     qword [rbp-8H], rdi
        mov     qword [rbp-10H], rsi
        mov     rdx, qword [rbp-10H]
        mov     rax, qword [rbp-8H]
        mov     rsi, rdx
        mov     rdi, rax
        call    strcmp
        cdqe
        leave
        ret


_lib_alloc:
        push    rbp
        mov     rbp, rsp
        sub     rsp, 48
        mov     qword [rbp-28H], rdi
        mov     qword [rbp-30H], rsi
        mov     rax, qword [rbp-30H]
        mov     rax, qword [rax]
        mov     qword [rbp-10H], rax
        mov     rax, qword [rbp-10H]
        add     rax, 1
        shl     rax, 3
        mov     rdi, rax
        call    malloc
        mov     qword [rbp-8H], rax
        mov     rax, qword [rbp-8H]
        mov     rdx, qword [rbp-10H]
        mov     qword [rax], rdx
        cmp     qword [rbp-28H], 1
        jnz     L_002
        mov     rax, qword [rbp-8H]
        jmp     L_005

L_002:  mov     dword [rbp-14H], 1
        jmp     L_004

L_003:  mov     rax, qword [rbp-30H]
        lea     rdx, [rax-8H]
        mov     rax, qword [rbp-28H]
        sub     rax, 1
        mov     rsi, rdx
        mov     rdi, rax
        call    _lib_alloc
        mov     rcx, rax
        mov     eax, dword [rbp-14H]
        cdqe
        lea     rdx, [rax*8]
        mov     rax, qword [rbp-8H]
        add     rax, rdx
        mov     rdx, rcx
        mov     qword [rax], rdx
        add     dword [rbp-14H], 1
L_004:  mov     eax, dword [rbp-14H]
        cdqe
        cmp     qword [rbp-10H], rax
        jge     L_003
        mov     rax, qword [rbp-8H]
L_005:  leave
        ret


SECTION .data


SECTION .bss


SECTION .rodata

L_006:
        db 25H, 64H, 00H

L_007:
        db 25H, 64H, 0AH, 00H

L_008:
        db 25H, 73H, 00H

global _func_printBool
global main
global _str_1
global _str_2
global _str_3
global _str_4
global _str_5
global _str_6

SECTION .text
_func_printBool:
	push	rbx
	push	r12
	push	r13
	push	r14
	push	r15
	push	rbp
	mov	rbp, rsp
	mov	r12, rdi
	cmp	r12, 1
	jne	_Label_19
_Label_22:
	mov	rdi, _str_2
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
_Label_16:
	pop	rbp
	pop	r15
	pop	r14
	pop	r13
	pop	r12
	pop	rbx
	ret
_Label_19:
	mov	rdi, _str_1
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	jmp	_Label_16
main:
	push	rbp
	mov	rbp, rsp
	mov	r14, _str_3
	mov	r15, _str_4
	mov	rcx, _str_5
	mov	rdx, 2
	mov	rsi, 0
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_str_substring
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	r12, rax
	mov	r13, r12
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jne	_Label_42
_Label_41:
	mov	r12, 1
_Label_40:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	je	_Label_51
_Label_50:
	mov	r12, 1
_Label_49:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jge	_Label_60
_Label_59:
	mov	r12, 1
_Label_58:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jle	_Label_69
_Label_68:
	mov	r12, 1
_Label_67:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jg	_Label_78
_Label_77:
	mov	r12, 1
_Label_76:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r13
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jl	_Label_87
_Label_86:
	mov	r12, 1
_Label_85:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rdi, _str_6
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jne	_Label_99
_Label_98:
	mov	r12, 1
_Label_97:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	je	_Label_108
_Label_107:
	mov	r12, 1
_Label_106:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jge	_Label_117
_Label_116:
	mov	r12, 1
_Label_115:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jle	_Label_126
_Label_125:
	mov	r12, 1
_Label_124:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jg	_Label_135
_Label_134:
	mov	r12, 1
_Label_133:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, r15
	mov	rdi, r14
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jl	_Label_144
_Label_143:
	mov	r12, 1
_Label_142:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rdi, _str_6
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_println
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jne	_Label_156
_Label_155:
	mov	r12, 1
_Label_154:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	je	_Label_165
_Label_164:
	mov	r12, 1
_Label_163:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jge	_Label_174
_Label_173:
	mov	r12, 1
_Label_172:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jle	_Label_183
_Label_182:
	mov	r12, 1
_Label_181:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jg	_Label_192
_Label_191:
	mov	r12, 1
_Label_190:
	mov	rdi, r12
	call	_func_printBool
	mov	r12, rax
	mov	rsi, rcx
	mov	rdi, r15
	push	rcx
	push	rdx
	push	r8
	push	r9
	push	rsi
	push	rdi
	call	_lib_strcmp
	pop	rdi
	pop	rsi
	pop	r9
	pop	r8
	pop	rdx
	pop	rcx
	cmp	rax, 0
	jl	_Label_201
_Label_200:
	mov	r12, 1
_Label_199:
	mov	rdi, r12
	call	_func_printBool
	mov	rbx, rax
	mov	rax, 0
	pop	rbp
	ret
_Label_201:
	mov	r12, 0
	jmp	_Label_199
_Label_192:
	mov	r12, 0
	jmp	_Label_190
_Label_183:
	mov	r12, 0
	jmp	_Label_181
_Label_174:
	mov	r12, 0
	jmp	_Label_172
_Label_165:
	mov	r12, 0
	jmp	_Label_163
_Label_156:
	mov	r12, 0
	jmp	_Label_154
_Label_144:
	mov	r12, 0
	jmp	_Label_142
_Label_135:
	mov	r12, 0
	jmp	_Label_133
_Label_126:
	mov	r12, 0
	jmp	_Label_124
_Label_117:
	mov	r12, 0
	jmp	_Label_115
_Label_108:
	mov	r12, 0
	jmp	_Label_106
_Label_99:
	mov	r12, 0
	jmp	_Label_97
_Label_87:
	mov	r12, 0
	jmp	_Label_85
_Label_78:
	mov	r12, 0
	jmp	_Label_76
_Label_69:
	mov	r12, 0
	jmp	_Label_67
_Label_60:
	mov	r12, 0
	jmp	_Label_58
_Label_51:
	mov	r12, 0
	jmp	_Label_49
_Label_42:
	mov	r12, 0
	jmp	_Label_40

SECTION .data
_str_1:
	db	46H, 61H, 6CH, 73H, 65H, 00H
_str_2:
	db	54H, 72H, 75H, 65H, 00H
_str_3:
	db	41H, 43H, 4DH, 00H
_str_4:
	db	41H, 43H, 4DH, 69H, 6CH, 61H, 6EH, 00H
_str_5:
	db	41H, 43H, 4DH, 43H, 6CH, 61H, 73H, 73H, 00H
_str_6:
	db	00H

SECTION .bss
