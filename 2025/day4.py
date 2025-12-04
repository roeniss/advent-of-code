lines = IN.split("\n")


def count(arr):
    return len(list(filter(lambda x: x == "@", arr)))


X = len(lines[0])
Y = len(lines)

S = set()
S.add("du-mmy")
ans = 0
while len(S) > 0:
    S = set()
    for x in range(0, X):
        for y in range(0, Y):
            a1 = lines[x - 1][y - 1] if x > 0 and y > 0 else "."
            a2 = lines[x][y - 1] if y > 0 else "."
            a3 = lines[x + 1][y - 1] if x < X - 1 and y > 0 else "."
            a4 = lines[x - 1][y] if x > 0 else "."
            a5 = lines[x][y]
            a6 = lines[x + 1][y] if x < X - 1 else "."
            a7 = lines[x - 1][y + 1] if x > 0 and y < Y - 1 else "."
            a8 = lines[x][y + 1] if y < Y - 1 else "."
            a9 = lines[x + 1][y + 1] if x < X - 1 and y < Y - 1 else "."

            if a5 == "@" and count([a1, a2, a3, a4, a6, a7, a8, a9]) < 4:
                S.add(str(x) + "-" + str(y))
    ans += len(S)
    for s in S:
        x,y = map(int, s.split("-"))

        l = list(lines[x])
        l[y] = 'x'
        lines[x] = "".join(l)

print(ans)

# for line in lines:
#     print(line)

