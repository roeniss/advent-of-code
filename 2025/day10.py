from z3 import *

lines = IN.split("\n")

# I am dirty
def solve_machine(buttons, targets):
  opt = Optimize()

  # 각 버튼 누름 횟수 = 정수 변수
  x = [Int(f'x{i}') for i in range(len(buttons))]

  # condition 1: 모든 x >= 0
  for xi in x:
    opt.add(xi >= 0)

  # condition 2: 각 카운터가 목표값에 도달
  for j in range(len(targets)):
    opt.add(Sum([x[i] for i in range(len(buttons)) if j in buttons[i]]) == targets[j])
  
  opt.minimize(Sum(x))
  
  if opt.check() == sat:
    return sum(opt.model()[xi].as_long() for xi in x)

  raise -1


def solve(line):
  goal=line.split(" ")[0]
  goal=goal[1:-1]
  goal = goal.replace(".", "0").replace("#", "1")
  goal = goal[::-1]
  goal = "0b"+goal
  goal = eval(goal)

  options = line.split("] ")[1].split(" {")[0]
  options = options.split(" ")
  options = [eval(o) for o in options]
  options = [set([o]) if isinstance(o, int) else set(o) for o in options]
  
  # part 2
  goal = list(map(int, line.split(") {")[1][0:-1].split(",")))
  a = solve_machine(options, goal)
  return a

  # part 1
  q = [[0, 0]] # [cur, cnt]
  vis = set()
  vis.add(0)
  
  while len(q) > 0:
    [cur, cnt] = q.pop(0)
    cnt+=1
    for o in options:
      t = cur
      for bit in o:
        t^=1<<bit
      
      if t in vis:
        continue
      if t == goal:
        return cnt
      vis.add(t)
      q.append([t, cnt])

ans = [solve(l) for l in lines]

print(ans)
print(sum(ans))