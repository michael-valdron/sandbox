package yaml

import (
	"strings"
	"testing"

	"gopkg.in/yaml.v3"
)

func TestParse(t *testing.T) {
	tests := []struct {
		name              string
		testfile          string
		expectedTopLevels []string
	}{
		{
			name:              "Test parsing",
			testfile:          "../../resources/test/parse_test.yaml",
			expectedTopLevels: []string{"metadata", "spec"},
		},
	}

	for _, testData := range tests {
		t.Run(testData.name, func(tt *testing.T) {
			topLevelLabels := make(map[string]bool)
			documentNode, err := parse(testData.testfile)
			if err != nil {
				tt.Fatalf("Unexpected error parsing the yaml content on test %s: %v", testData.name, err)
			}

			if documentNode.Content[0].Kind == yaml.SequenceNode {
				tt.Fatalf("Not expecting a sequence node")
			}

			for _, topLevel := range testData.expectedTopLevels {
				topLevelLabels[topLevel] = false
			}

			for _, topLevel := range documentNode.Content[0].Content {
				_, found := topLevelLabels[topLevel.Value]
				if topLevel.Value != "" && !found {
					tt.Errorf("%s is not in the list of expected roots: %s", topLevel.Value, strings.Join(testData.expectedTopLevels, ", "))
				} else {
					topLevelLabels[topLevel.Value] = true
				}
			}

			for _, topLevel := range testData.expectedTopLevels {
				if !topLevelLabels[topLevel] {
					tt.Errorf("Expected %s was not found among roots", topLevel)
				}
			}
		})
	}
}
