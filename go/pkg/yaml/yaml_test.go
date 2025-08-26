package yaml

import (
	"reflect"
	"testing"
)

func TestParse(t *testing.T) {
	tests := []struct {
		name              string
		testfile          string
		expectedType      reflect.Kind
		expectedTopLevels []any
	}{
		{
			name:              "Test parsing",
			testfile:          "../../resources/test/parse_test.yaml",
			expectedType:      reflect.Map,
			expectedTopLevels: []any{"metadata", "spec"},
		},
	}

	for _, testData := range tests {
		t.Run(testData.name, func(tt *testing.T) {
			document, err := Parse(testData.testfile)
			if err != nil {
				tt.Fatalf("Unexpected error parsing the yaml content on test %s: %v", testData.name, err)
			}

			documentObject := reflect.ValueOf(document)

			if documentObject.Kind() != testData.expectedType {
				tt.Fatalf("Expected result type: %v, Got result type: %v\n", testData.expectedType, documentObject.Kind())
			}

			for _, expectedTopLevel := range testData.expectedTopLevels {
				switch documentObject.Kind() {
				case reflect.Map:
					key := reflect.ValueOf(expectedTopLevel)
					child := documentObject.MapIndex(key)
					if !child.IsValid() {
						tt.Errorf("Expected %s was not found among roots", expectedTopLevel)
					}
				case reflect.Slice:
					idx := reflect.ValueOf(expectedTopLevel)
					if !idx.CanInt() {
						tt.Fatalf("Unexpected slice where index is non-int")
					}

					if int(idx.Int()) >= documentObject.Len() {
						tt.Errorf("Expected %d to be in the range of the top level list", idx.Int())
					}
				default:
					if len(testData.expectedTopLevels) != 0 {
						tt.Errorf("Was expected top level accessors to exists, got scalar instead.")
					}
				}
			}
		})
	}
}

func TestDepth(t *testing.T) {
	tests := []struct {
		name          string
		input         any
		expectedDepth int
	}{
		{
			name:          "Test depth case 1",
			input:         "test",
			expectedDepth: 0,
		},
		{
			name: "Test depth case 2",
			input: map[string]any{
				"name": "Example",
				"spec": nil,
			},
			expectedDepth: 1,
		},
		{
			name: "Test depth case 3",
			input: map[string]any{
				"metadata": map[string]any{
					"name": "Example",
				},
				"spec": map[string]any{
					"containers": []map[string]any{
						{
							"name":  "test-container",
							"image": "docker.io/library/golang:1.24",
						},
					},
				},
			},
			expectedDepth: 4,
		},
	}

	for _, testData := range tests {
		t.Run(testData.name, func(tt *testing.T) {
			result := Depth(testData.input)

			if result != testData.expectedDepth {
				tt.Errorf("Expected: %d, Got: %d\n", testData.expectedDepth, result)
			}
		})
	}
}

func TestExists(t *testing.T) {
	tests := []struct {
		name     string
		input    any
		ids      []any
		expected bool
	}{
		{
			name:     "Test exists case 1",
			input:    "test",
			ids:      []any{0},
			expected: false,
		},
		{
			name: "Test exists case 2",
			input: map[string]any{
				"name": "Example",
				"spec": nil,
			},
			ids:      []any{"spec"},
			expected: true,
		},
		{
			name: "Test exists case 3",
			input: map[string]any{
				"metadata": map[string]any{
					"name": "Example",
				},
				"spec": map[string]any{
					"containers": []map[string]any{
						{
							"name":  "test-container",
							"image": "docker.io/library/golang:1.24",
						},
					},
				},
			},
			ids:      []any{"spec", "containers", 0, "image"},
			expected: true,
		},
		{
			name: "Test exists case 4",
			input: map[string]any{
				"metadata": map[string]any{
					"name": "Example",
				},
				"spec": map[string]any{
					"containers": []map[string]any{
						{
							"name":  "test-container",
							"image": "docker.io/library/golang:1.24",
						},
					},
				},
			},
			ids:      []any{"spec", "containers", 0, "pullPolicy"},
			expected: false,
		},
		{
			name: "Test exists case 5",
			input: map[string]any{
				"metadata": map[string]any{
					"name": "Example",
				},
				"spec": map[string]any{
					"containers": []map[string]any{
						{
							"name":  "test-container",
							"image": "docker.io/library/golang:1.24",
						},
					},
				},
			},
			ids:      []any{"spec", "containers", 2, "image"},
			expected: false,
		},
	}

	for _, testData := range tests {
		t.Run(testData.name, func(tt *testing.T) {
			result := Exists(testData.input, testData.ids...)

			if result != testData.expected {
				tt.Errorf("Expected: %v, Got: %v\n", testData.expected, result)
			}
		})
	}
}

func TestGet(t *testing.T) {
	tests := []struct {
		name          string
		input         any
		ids           []any
		expectedValue any
	}{
		{
			name:          "Test get case 1",
			input:         "test",
			ids:           []any{0},
			expectedValue: nil,
		},
		{
			name: "Test get case 2",
			input: map[string]any{
				"name": "Example",
				"spec": nil,
			},
			ids:           []any{"spec"},
			expectedValue: nil,
		},
		{
			name: "Test get case 3",
			input: map[string]any{
				"metadata": map[string]any{
					"name": "Example",
				},
				"spec": map[string]any{
					"containers": []map[string]any{
						{
							"name":  "test-container",
							"image": "docker.io/library/golang:1.24",
						},
					},
				},
			},
			ids:           []any{"spec", "containers", 0, "image"},
			expectedValue: "docker.io/library/golang:1.24",
		},
	}

	for _, testData := range tests {
		t.Run(testData.name, func(tt *testing.T) {
			result := Get(testData.input, testData.ids...)

			if result != testData.expectedValue {
				tt.Errorf("Expected: %v, Got: %v\n", testData.expectedValue, result)
			}
		})
	}
}
