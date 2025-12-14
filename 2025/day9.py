lines = IN.split("\n")

edges = []
pairs = []
cx, cy = map(int, lines[-1].split(","))
for line in lines:
  x, y = map(int, line.split(","))
  pairs.append([x,y])
  edges.append([cx, cy, x, y])
  cx, cy = x, y

def is_valid(px, py, edges): # 사각형 영역, 변 정보를 담은 list
  count = 0
  for (x1, y1, x2, y2) in edges:
    # 수평 변이면 스킵
    if y1 == y2:
      continue
      
    # 수직 변이랑 교차 체크
    min_y, max_y = min(y1, y2), max(y1, y2)
    if min_y <= py < max_y and x1 >= px:
      count += 1

  return count % 2 == 1

def on_edge(px, py, edges):
    for (x1, y1, x2, y2) in edges:
        if x1 == x2:  # 수직 변
            min_y, max_y = min(y1, y2), max(y1, y2)
            if px == x1 and min_y <= py <= max_y:
                return True
        else:  # 수평 변
            min_x, max_x = min(x1, x2), max(x1, x2)
            if py == y1 and min_x <= px <= max_x:
                return True
    return False

def is_inside_or_on_edge(px, py, edges):
    return is_valid(px, py, edges) or on_edge(px, py, edges)
      
def segments_intersect(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2):
    # 두 선분이 교차하는지 (끝점 제외)
    # 선분 A: (ax1,ay1)-(ax2,ay2), 선분 B: (bx1,by1)-(bx2,by2)
    def ccw(px, py, qx, qy, rx, ry):
        return (qx-px)*(ry-py) - (qy-py)*(rx-px)
    
    d1 = ccw(ax1, ay1, ax2, ay2, bx1, by1)
    d2 = ccw(ax1, ay1, ax2, ay2, bx2, by2)
    d3 = ccw(bx1, by1, bx2, by2, ax1, ay1)
    d4 = ccw(bx1, by1, bx2, by2, ax2, ay2)
    
    if d1 * d2 < 0 and d3 * d4 < 0:
        return True
    return False

def rect_inside_polygon(rx1, ry1, rx2, ry2, edges):
    # 1. 네 꼭짓점이 다각형 안에 있는지
    corners = [(rx1,ry1), (rx1,ry2), (rx2,ry1), (rx2,ry2)]
    for (cx, cy) in corners:
        if not is_inside_or_on_edge(cx, cy, edges):
            return False
    
    # 2. 직사각형 변이 다각형 변과 교차하는지
    rect_edges = [
        (rx1, ry1, rx2, ry1),  # 위
        (rx1, ry2, rx2, ry2),  # 아래
        (rx1, ry1, rx1, ry2),  # 왼쪽
        (rx2, ry1, rx2, ry2),  # 오른쪽
    ]
    for (ex1, ey1, ex2, ey2) in edges:
        for (rx_a, ry_a, rx_b, ry_b) in rect_edges:
            if segments_intersect(ex1, ey1, ex2, ey2, rx_a, ry_a, rx_b, ry_b):
                return False
    return True

ans = 0
for i in range(len(pairs)):
    for j in range(i+1, len(pairs)):
        [x1, y1] = pairs[i]
        [x2, y2] = pairs[j]
        if rect_inside_polygon(min(x1,x2), min(y1,y2), max(x1,x2), max(y1,y2), edges):
            ans = max(ans, (abs(y1 - y2) + 1) * (abs(x1 - x2) + 1))
            
print(ans)


# arr = [["."] * 15 for _ in range(9)]
# def debug():
#   for a in arr:
#     for aa in a:
#       print(aa, end ="")
#     print("")

# for x in range(15):
#   for y in range(9):
#     arr[y][x] = "T" if is_inside_or_on_edge(x, y, edges) else "."

# debug()

# print(is_valid(7, 1, edges))