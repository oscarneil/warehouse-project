import matplotlib.pyplot as plt
import numpy as np

plt.rcParams['font.family'] = 'DFKai-SB'
plt.rcParams.update({'font.size': 22})


data = np.array([
    0.002038,
    0.001086,
    0.001328,
    0.001540,
    0.004067,
    0.007227,
    0.013833,
    0.020756,
    0.0201842,
    1.078907,
    4.550078,
    24.357929,
    82.459404,
    1000
    
    ])
data2 = np.array([
    0.003558,
    0.004632,
    0.008125,
    0.010299,
    0.014266,
    0.016753,
    0.022292,
    0.028022,
    0.031693,
    0.038043,
    0.04281,
    0.051447,
    0.056381,
    0.062501
    ])
# Create a line chart
x = np.arange(1, len(data) + 1)
plt.figure(figsize=(12, 7))
plt.ylim([-0.1,1.0])
plt.plot(x, data, label='揀貨演算法(優化)', marker='o', linestyle='-')
plt.plot(x, data2, label='蟻群演算法', marker='o', linestyle='-')

# Add labels and a legend
plt.xlabel('揀貨數量')
plt.ylabel('時間(秒)')
plt.title('揀貨數量-時間')

plt.legend()

# Show the plot
plt.grid(True)
plt.show()