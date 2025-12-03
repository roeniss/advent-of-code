ans = 0
for line in IN.split("\n"):
    line = list(map(int, line))
    L = line[-12:]
    for x in line[0:-12][::-1]:
        if x >= L[0]:
            L.insert(0, x)
            found = False
            for pos in range(1, len(L)-1):
                if L[pos] < L[pos+1]:
                    found = True
                    L.pop(pos)
                    break
            if not found:
                L.pop()
                
            # failed approach ~
            # found = False
            # for value in range(1, 10):
            #     if found:
            #         break
            #     for pos in range(1, len(L)):
            #         if L[pos] == value:
            #             found = True
            #             L.pop(pos)
            #             break
            # ~ failed approach
    v = int("".join(map(str, L)))
    ans += v
    
print(ans)
            
    