IN=""
ans = 0
SET = set()
for rng in IN.split(","):
    l, r = map(int, rng.split("-"))
    # print(l, r)
    for num in range(l, r+1):
        # print(i)
        s = str(num)
        L = len(s)
        for parts in range(2, L+1):
            if L % parts != 0:
                continue
            n = L // parts
            chunks = [s[i*n:(i+1)*n] for i in range(parts)]
            if len(set(chunks)) == 1:
                # print(chunks[0], num, chunks)
                ans += num
                break
print(ans)