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
	jne	_Label_43
_Label_42:
	mov	r12, 1
_Label_41:
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
	je	_Label_53
_Label_52:
	mov	r12, 1
_Label_51:
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
	jge	_Label_63
_Label_62:
	mov	r12, 1
_Label_61:
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
	jle	_Label_73
_Label_72:
	mov	r12, 1
_Label_71:
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
	jg	_Label_83
_Label_82:
	mov	r12, 1
_Label_81:
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
	jl	_Label_93
_Label_92:
	mov	r12, 1
_Label_91:
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
	jne	_Label_106
_Label_105:
	mov	r12, 1
_Label_104:
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
	je	_Label_116
_Label_115:
	mov	r12, 1
_Label_114:
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
	jge	_Label_126
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
	jle	_Label_136
_Label_135:
	mov	r12, 1
_Label_134:
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
	jg	_Label_146
_Label_145:
	mov	r12, 1
_Label_144:
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
	jl	_Label_156
_Label_155:
	mov	r12, 1
_Label_154:
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
	jne	_Label_169
_Label_168:
	mov	r12, 1
_Label_167:
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
	je	_Label_179
_Label_178:
	mov	r12, 1
_Label_177:
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
	jge	_Label_189
_Label_188:
	mov	r12, 1
_Label_187:
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
	jle	_Label_199
_Label_198:
	mov	r12, 1
_Label_197:
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
	jg	_Label_209
_Label_208:
	mov	r12, 1
_Label_207:
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
	jl	_Label_219
_Label_218:
	mov	r12, 1
_Label_217:
	mov	rdi, r12
	call	_func_printBool
	mov	rbx, rax
	mov	rax, 0
	pop	rbp
	ret
_Label_219:
	mov	r12, 0
	jmp	_Label_217
_Label_209:
	mov	r12, 0
	jmp	_Label_207
_Label_199:
	mov	r12, 0
	jmp	_Label_197
_Label_189:
	mov	r12, 0
	jmp	_Label_187
_Label_179:
	mov	r12, 0
	jmp	_Label_177
_Label_169:
	mov	r12, 0
	jmp	_Label_167
_Label_156:
	mov	r12, 0
	jmp	_Label_154
_Label_146:
	mov	r12, 0
	jmp	_Label_144
_Label_136:
	mov	r12, 0
	jmp	_Label_134
_Label_126:
	mov	r12, 0
	jmp	_Label_124
_Label_116:
	mov	r12, 0
	jmp	_Label_114
_Label_106:
	mov	r12, 0
	jmp	_Label_104
_Label_93:
	mov	r12, 0
	jmp	_Label_91
_Label_83:
	mov	r12, 0
	jmp	_Label_81
_Label_73:
	mov	r12, 0
	jmp	_Label_71
_Label_63:
	mov	r12, 0
	jmp	_Label_61
_Label_53:
	mov	r12, 0
	jmp	_Label_51
_Label_43:
	mov	r12, 0
	jmp	_Label_41

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
