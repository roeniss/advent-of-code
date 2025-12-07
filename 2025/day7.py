from functools import lru_cache
import re


def debug(lines):
    for l in lines:
        print("".join(map(str, l)))


lines = re.sub("S", "1", IN)
lines = re.sub(r"\.", "0", lines)
lines = lines.split("\n")
lines = [list(l) for l in lines]
for i in range(len(lines)):
    for j in range(len(lines[0])):
        if lines[i][j] != "^":
            lines[i][j] = int(lines[i][j])

ans = 0
i = 0
while i < len(lines) - 1:
    i += 1
    for j, l in enumerate(lines[i]):
        if l == "^":
            if int(lines[i - 1][j]) > 0:
                ans += 1
            if j > 0:
                lines[i][j - 1] = int(lines[i][j - 1]) + int(lines[i - 1][j])
            if j < len(lines[i]) - 1:
                lines[i][j + 1] = int(lines[i][j + 1]) + int(lines[i - 1][j])
            lines[i][j] = 0
        else:
            lines[i][j] = int(lines[i][j]) + int(lines[i - 1][j])
    # debug(lines)

print(ans)

# part 2 (failed to forecast)

lines = IN.split("\n")
lines = [list(l) for l in lines]
y, x = 0, lines[0].index("S")


@lru_cache(None)
def dp(y, x):
    if not 0 <= x < len(lines[0]):
        return 0
    if y == len(lines):
        return 1
    elif lines[y][x] == ".":
        return dp(y + 1, x)
    else:
        return dp(y + 1, x + 1) + dp(y + 1, x - 1)


print(dp(y, x))
